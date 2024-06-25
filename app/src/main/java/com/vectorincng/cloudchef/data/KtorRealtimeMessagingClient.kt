package com.vectorincng.cloudchef.data

import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.DefaultClientWebSocketSession
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class KtorRealtimeMessagingClient(private val client: HttpClient): RealTimeMessagingClient {
    private var session : WebSocketSession? = null

    override fun getAppStateStream(): Flow<MessageState> {
        return callbackFlow {
            var session: DefaultClientWebSocketSession? = null

            while (true) {
                try {
                    session = client.webSocketSession {
                        url("ws://192.168.1.23:8080/play")
                    }

                    session.incoming.consumeAsFlow()
                        .filterIsInstance<Frame.Text>()
                        .mapNotNull { frame ->
                            try {
                                Json.decodeFromString<MessageState>(frame.readText())
                            } catch (e: SerializationException) {
                                null
                            }
                        }
                        .collect { messageState ->
                            trySend(messageState).isSuccess
                        }
                } catch (e: Exception) {
                    println("WebSocket connection failed: ${e.message}")
                    delay(5000)
                } finally {
                    session?.close(CloseReason(CloseReason.Codes.NORMAL, "WebSocket session closed"))
                    session = null
                }
            }
        }
    }

    override suspend fun sendAction(action: MakeTurn) {
        session?.outgoing?.send(
            Frame.Text("make_turn#${Json.encodeToString(action)}")
        )
    }

    override suspend fun closeConnection() {
        session?.close()
        session = null
    }
}