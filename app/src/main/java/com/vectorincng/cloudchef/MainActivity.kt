package com.vectorincng.cloudchef

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vectorincng.cloudchef.presentation.ClientViewModel
import com.vectorincng.cloudchef.presentation.GameField
import com.vectorincng.cloudchef.ui.theme.CloudChefTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CloudChefTheme {
                val viewModel = hiltViewModel<ClientViewModel>()
                val state by viewModel.state.collectAsState()
                val isConnecting by viewModel.isConnecting.collectAsState()
                val showConnectionError by viewModel.showConnectionError.collectAsState()

                Log.d("Hello", state.connectedPlayer.size.toString())

                if(showConnectionError) {
                    Box(modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "Couldn't Connect to the server", color = MaterialTheme.colorScheme.error)
                    }
                    return@CloudChefTheme
                }

                Box(modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center) {

                    Column(modifier = Modifier
                        .padding(top = 35.dp)
                        .align(Alignment.TopEnd)) {

                        if(!state.connectedPlayer.contains('X')) {
                            Text(text = "Waiting for player X",
                                fontSize = 25.sp)
                        } else if (!state.connectedPlayer.contains('O')) {
                            Text(text = "Waiting for player O",
                                fontSize = 25.sp)
                        }

                        if(state.connectedPlayer.size == 2 && state.winningPlayer == null && !state.isBoardFull) {
                            Text(
                                text = if(state.playerAtTurn == 'X') {
                                    "X is next"
                                } else "O is next",
                                fontSize = 32.sp,
                            )
                        }

                        GameField(state = state, onTapInField = viewModel::finishTurn, modifier = Modifier.fillMaxWidth())
                    }

                    if(state.isBoardFull || state.winningPlayer != null) {
                        Text(
                            text = when(state.winningPlayer) {
                                'X' -> "Player X won!"
                                'O' -> "Player O won!"
                                else -> "It's a draw!"
                            },
                            fontSize = 32.sp,
                            modifier = Modifier
                                .padding(bottom = 32.dp)
                                .align(Alignment.BottomCenter)
                        )
                    }

                    if(isConnecting) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CloudChefTheme {
        Greeting("Android")
    }
}