package com.vectorincng.cloudchef

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vectorincng.cloudchef.presentation.ClientViewModel
import com.vectorincng.cloudchef.presentation.ConnectivityStatus
import com.vectorincng.cloudchef.ui.theme.CloudChefTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CloudChefTheme {
                val viewModel = hiltViewModel<ClientViewModel>()
                val state by viewModel.state.collectAsState()
                val isConnecting by viewModel.isConnecting.collectAsState()

                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    ConnectivityStatus(isConnecting = isConnecting)

                    Column(
                        modifier = Modifier
                            .padding(top = 35.dp).align(Alignment.Center)
                    ) {
                        Text(text = state.messageData ?: "", fontSize = 15.sp)
                    }
                }
            }
        }
    }
}

