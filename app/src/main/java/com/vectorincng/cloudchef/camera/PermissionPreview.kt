package com.vectorincng.cloudchef.camera

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.PermissionState

import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissions() {
    val permissionStates = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    LaunchedEffect(permissionStates) {
        permissionStates.launchMultiplePermissionRequest()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        permissionStates.permissions.forEach { permissionState ->
            when (permissionState.permission) {
                Manifest.permission.CAMERA -> {
                    HandleCameraPermissionState(permissionState)
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HandleCameraPermissionState(permissionState: PermissionState) {
    when {
        permissionState.status.isGranted -> {
            Text(text = "Camera permission granted")
        }
        !permissionState.status.isGranted -> {
            if (permissionState.status.shouldShowRationale) {
                Text(text = "Camera permission is needed")
            } else {
                Text(text = "Navigate to settings and enable the Camera permission")
            }
        }
        else -> {
            Text(text = "Permission status unknown")
        }
    }
}