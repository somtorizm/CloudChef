package com.vectorincng.cloudchef.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.vectorincng.cloudchef.data.MessageState


@Composable
fun AppUI(
    status: Boolean,
    state: MessageState,
    modifier: Modifier = Modifier,
) {
    Column(modifier.fillMaxWidth()) {
        ConnectivityStatus(isConnecting = status)
        Text(text = state.messageData ?: "", fontSize = 25.sp)
    }
}


