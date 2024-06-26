package com.vectorincng.cloudchef.data

import kotlinx.coroutines.flow.Flow

interface RealTimeMessagingClient {
    fun getAppStateStream() : Flow<MessageState>

    suspend fun sendAction(action: String)

    suspend fun closeConnection()
}