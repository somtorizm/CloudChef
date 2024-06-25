package com.vectorincng.cloudchef.data

import io.ktor.client.HttpClient
import io.ktor.client.features.websocket.webSocketSession
import io.ktor.client.request.url
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.close
import io.ktor.http.cio.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class KtorRealtimeMessagingClient(private val client: HttpClient): RealTimeMessagingClient {
    private var session : WebSocketSession? = null

    override fun getAppStateStream(): Flow<MessageState> {
        return flow {
          session = client.webSocketSession {
             url("ws://192.168.1.23:8080/play")
          }
            val appState = session!!.incoming.consumeAsFlow().filterIsInstance<Frame.Text>()
                .mapNotNull { Json.decodeFromString<MessageState>( it.readText()) }

            emitAll(appState)
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