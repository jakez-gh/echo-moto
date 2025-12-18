package com.echo.companion

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.echo.companion.service.AudioRecordingService
import com.echo.companion.ui.theme.EchoCompanionTheme
import com.google.accompanist.permissions.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setContent {
            EchoCompanionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EchoMainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EchoMainScreen() {
    val viewModel: MainViewModel = viewModel()
    val coroutineScope = rememberCoroutineScope()
    
    // Permission states
    val audioPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    val notificationPermissionState = rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
    val locationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    
    var isRecording by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    var listeningIndicator by remember { mutableStateOf(0f) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Title
        Text(
            text = "Echo Companion",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 24.dp)
        )
        
        // Subtitle - First Milestone Message
        Text(
            text = "Echo Lives! 🎯",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.secondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Listening indicator (subtle pulse)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isRecording) 
                    MaterialTheme.colorScheme.primaryContainer 
                else 
                    MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isRecording) {
                    // Pulsing indicator when listening
                    Text(
                        text = "◉ Listening...",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                } else {
                    Text(
                        text = "○ Idle",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        // Permission Status Cards
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Permissions Status",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                PermissionRow(
                    permission = "Audio Recording",
                    isGranted = audioPermissionState.status.isGranted,
                    onRequest = { audioPermissionState.launchPermissionRequest() }
                )
                
                PermissionRow(
                    permission = "Notifications",
                    isGranted = notificationPermissionState.status.isGranted,
                    onRequest = { notificationPermissionState.launchPermissionRequest() }
                )
                
                PermissionRow(
                    permission = "Location",
                    isGranted = locationPermissionState.status.isGranted,
                    onRequest = { locationPermissionState.launchPermissionRequest() }
                )
            }
        }
        
        // Control Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Start/Stop Recording Button
            Button(
                onClick = {
                    if (audioPermissionState.status.isGranted) {
                        isRecording = !isRecording
                        // TODO: Start/stop audio service
                    } else {
                        audioPermissionState.launchPermissionRequest()
                    }
                },
                modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRecording) 
                        MaterialTheme.colorScheme.error 
                    else 
                        MaterialTheme.colorScheme.primary
                )
            ) {
                Text(if (isRecording) "Stop Recording" else "Start Recording")
            }
            
            // Settings Button
            Button(
                onClick = { showSettings = true },
                modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Settings")
            }
        }
        
        // Info Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Text(
                text = """
                    Echo is your personal on-device AI companion.
                    
                    • Privacy-first: All data stays on your device
                    • Silence by default: Only responds when confident
                    • Learns from your interactions
                    • Manual call recording trigger
                    • Battery-aware learning
                """.trimIndent(),
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontSize = 14.sp
            )
        }
        
        // Development Info
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Text(
                text = """
                    Development Build v1.0.0
                    Target: Moto G (Android 15/16)
                    TDD-driven development
                """.trimIndent(),
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun PermissionRow(
    permission: String,
    isGranted: Boolean,
    onRequest: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = permission,
            fontSize = 14.sp
        )
        if (isGranted) {
            Text(
                text = "✓ Granted",
                color = Color.Green,
                fontSize = 14.sp
            )
        } else {
            TextButton(onClick = onRequest) {
                Text("Request", fontSize = 14.sp)
            }
        }
    }
}