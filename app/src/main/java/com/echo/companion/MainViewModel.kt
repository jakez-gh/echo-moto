package com.echo.companion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class EchoState(
    val isListening: Boolean = false,
    val isLearning: Boolean = false,
    val batteryLevel: Int = 100,
    val chargeStatus: ChargeStatus = ChargeStatus.UNPLUGGED,
    val surpriseLevel: Float = 0f,
    val lastTranscription: String = "",
    val confidenceLevel: Float = 0f
)

enum class ChargeStatus {
    CHARGING, UNPLUGGED, FULL
}

class MainViewModel : ViewModel() {
    
    private val _state = MutableStateFlow(EchoState())
    val state: StateFlow<EchoState> = _state.asStateFlow()
    
    fun startListening() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isListening = true)
            // TODO: Start audio recording service
        }
    }
    
    fun stopListening() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isListening = false)
            // TODO: Stop audio recording service
        }
    }
    
    fun updateBatteryStatus(level: Int, isCharging: Boolean) {
        val status = when {
            level >= 100 -> ChargeStatus.FULL
            isCharging -> ChargeStatus.CHARGING
            else -> ChargeStatus.UNPLUGGED
        }
        _state.value = _state.value.copy(
            batteryLevel = level,
            chargeStatus = status
        )
    }
    
    fun updateSurpriseLevel(level: Float) {
        _state.value = _state.value.copy(surpriseLevel = level)
    }
    
    fun updateTranscription(text: String, confidence: Float) {
        _state.value = _state.value.copy(
            lastTranscription = text,
            confidenceLevel = confidence
        )
    }
}