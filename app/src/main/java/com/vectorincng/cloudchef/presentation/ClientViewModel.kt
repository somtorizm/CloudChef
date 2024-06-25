package com.vectorincng.cloudchef.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vectorincng.cloudchef.data.MakeTurn
import com.vectorincng.cloudchef.data.MessageState
import com.vectorincng.cloudchef.data.RealTimeMessagingClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val client: RealTimeMessagingClient
): ViewModel() {

    val state = client.getAppStateStream().onStart {
        _isConnecting.value = true
    }.onEach {
        _isConnecting.value = false
    }.catch { t ->
        println(t.message.toString() + "error")
        _showConnectionError.value = t is ConnectException
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MessageState())

    private val _isConnecting = MutableStateFlow(false)
    val isConnecting = _isConnecting.asStateFlow()

    private val _showConnectionError = MutableStateFlow(false)
    val showConnectionError = _showConnectionError.asStateFlow()


    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            client.closeConnection()
        }
    }
}