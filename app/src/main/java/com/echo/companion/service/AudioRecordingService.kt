package com.echo.companion.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.echo.companion.MainActivity
import com.echo.companion.R
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.atomic.AtomicBoolean

class AudioRecordingService : Service() {
    
    companion object {
        const val CHANNEL_ID = "EchoAudioChannel"
        const val NOTIFICATION_ID = 1
        const val ACTION_START_RECORDING = "com.echo.companion.START_RECORDING"
        const val ACTION_STOP_RECORDING = "com.echo.companion.STOP_RECORDING"
        
        // Audio recording parameters
        const val SAMPLE_RATE = 16000  // 16kHz for speech recognition
        const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
        const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT
    }
    
    private var audioRecord: AudioRecord? = null
    private var recordingJob: Job? = null
    private val isRecording = AtomicBoolean(false)
    private val serviceScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_RECORDING -> startRecording()
            ACTION_STOP_RECORDING -> stopRecording()
        }
        return START_STICKY
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Echo Audio Recording",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Echo is listening to help you"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        val stopIntent = Intent(this, AudioRecordingService::class.java).apply {
            action = ACTION_STOP_RECORDING
        }
        val stopPendingIntent = PendingIntent.getService(
            this, 1, stopIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Echo Companion")
            .setContentText("Listening for your needs...")
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .setContentIntent(pendingIntent)
            .addAction(android.R.drawable.ic_media_pause, "Stop", stopPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true)
            .build()
    }
    
    private fun startRecording() {
        if (isRecording.get()) return
        
        startForeground(NOTIFICATION_ID, createNotification())
        
        val bufferSize = AudioRecord.getMinBufferSize(
            SAMPLE_RATE,
            CHANNEL_CONFIG,
            AUDIO_FORMAT
        )
        
        try {
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE,
                CHANNEL_CONFIG,
                AUDIO_FORMAT,
                bufferSize
            )
            
            audioRecord?.startRecording()
            isRecording.set(true)
            
            recordingJob = serviceScope.launch {
                val audioBuffer = ShortArray(bufferSize)
                val outputFile = File(filesDir, "audio_${System.currentTimeMillis()}.pcm")
                
                FileOutputStream(outputFile).use { outputStream ->
                    while (isRecording.get() && isActive) {
                        val readCount = audioRecord?.read(audioBuffer, 0, bufferSize) ?: 0
                        if (readCount > 0) {
                            // Convert short array to byte array for storage
                            val byteBuffer = ByteArray(readCount * 2)
                            for (i in 0 until readCount) {
                                byteBuffer[i * 2] = (audioBuffer[i].toInt() and 0xFF).toByte()
                                byteBuffer[i * 2 + 1] = (audioBuffer[i].toInt() shr 8).toByte()
                            }
                            outputStream.write(byteBuffer)
                            
                            // TODO: Process audio buffer for transcription
                            processAudioBuffer(audioBuffer, readCount)
                        }
                        
                        // Small delay to prevent CPU overload
                        delay(50)
                    }
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
            stopSelf()
        }
    }
    
    private fun processAudioBuffer(buffer: ShortArray, size: Int) {
        // TODO: Implement audio processing
        // This is where we would:
        // 1. Buffer audio for transcription
        // 2. Run local STT (Speech-to-Text)
        // 3. Calculate surprise metrics
        // 4. Trigger responses based on confidence
    }
    
    private fun stopRecording() {
        isRecording.set(false)
        recordingJob?.cancel()
        audioRecord?.apply {
            stop()
            release()
        }
        audioRecord = null
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        stopRecording()
        serviceScope.cancel()
        super.onDestroy()
    }
}