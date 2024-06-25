package com.vectorincng.cloudchef.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vectorincng.cloudchef.data.MessageState
import com.vectorincng.cloudchef.data.RealTimeMessagingClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val client: RealTimeMessagingClient
): ViewModel() {

    private val _state = MutableStateFlow(MessageState())
    val state: StateFlow<MessageState> = _state

    private val _isConnecting = MutableStateFlow(false)
    val isConnecting: StateFlow<Boolean> = _isConnecting

    private var appStateJob: Job? = null

    init {
        startCollectingAppState()
    }

    private fun startCollectingAppState() {
        appStateJob?.cancel()

        appStateJob = viewModelScope.launch {
            client.getAppStateStream()
                .onStart { _isConnecting.value = true }
                .onEach { newState ->
                    _isConnecting.value = false
                    _state.value = newState
                }
                .retryWhen { cause, attempt ->
                    delay(5000)
                    true
                }
                .collect()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            client.closeConnection()
        }
    }
}