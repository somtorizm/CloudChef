package com.vectorincng.cloudchef.data

import kotlinx.coroutines.flow.Flow

interface RealTimeMessagingClient {
    fun getAppStateStream() : Flow<AppState>

    suspend fun sendAction(action: MakeTurn)

    suspend fun closeConnection()
}