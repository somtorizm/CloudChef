package com.vectorincng.cloudchef

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vectorincng.cloudchef.camera.CameraPreview
import com.vectorincng.cloudchef.camera.RequestPermissions
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val viewModel = hiltViewModel<ClientViewModel>()
                    val state by viewModel.state.collectAsState()
                    val isConnecting by viewModel.isConnecting.collectAsState()
                    val barCodeVal = remember { mutableStateOf("") }

                    CameraPreview() { code ->
                        barCodeVal.value = code.value
                    }

                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            RequestPermissions()

                            Text(
                                text = "QR CODE",
                                fontSize = 16.sp,
                                fontStyle = FontStyle.Italic
                            )

                            Text(
                                text = barCodeVal.value,
                                fontSize = 15.sp,
                            )

                            Spacer(modifier = Modifier.height(100.dp))

                            Text(
                                text = state.messageData ?: "",
                                fontSize = 15.sp,
                            )

                            Spacer(modifier = Modifier.height(100.dp))

                        }

                        ConnectivityStatus(isConnecting = isConnecting)
                    }
                }
            }
        }
    }
}

