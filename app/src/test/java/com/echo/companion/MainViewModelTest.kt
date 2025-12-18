package com.echo.companion

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class MainViewModelTest {
    
    private lateinit var viewModel: MainViewModel
    
    @Before
    fun setup() {
        viewModel = MainViewModel()
    }
    
    @Test
    fun `initial state should have listening as false`() {
        // Given: Initial state
        val initialState = viewModel.state.value
        
        // Then: Listening should be false
        assertFalse(initialState.isListening)
    }
    
    @Test
    fun `initial state should have full battery`() {
        // Given: Initial state
        val initialState = viewModel.state.value
        
        // Then: Battery should be at 100%
        assertEquals(100, initialState.batteryLevel)
        assertEquals(ChargeStatus.UNPLUGGED, initialState.chargeStatus)
    }
    
    @Test
    fun `surprise level should be zero initially`() {
        // Given: Initial state
        val initialState = viewModel.state.value
        
        // Then: Surprise level should be 0
        assertEquals(0f, initialState.surpriseLevel)
    }
    
    @Test
    fun `updateBatteryStatus should update battery level and charge status`() {
        // When: Battery is updated to 75% and charging
        viewModel.updateBatteryStatus(75, true)
        
        // Then: State should reflect the update
        val state = viewModel.state.value
        assertEquals(75, state.batteryLevel)
        assertEquals(ChargeStatus.CHARGING, state.chargeStatus)
    }
    
    @Test
    fun `updateBatteryStatus should mark as FULL when battery is 100 percent`() {
        // When: Battery is at 100%
        viewModel.updateBatteryStatus(100, false)
        
        // Then: Charge status should be FULL
        val state = viewModel.state.value
        assertEquals(100, state.batteryLevel)
        assertEquals(ChargeStatus.FULL, state.chargeStatus)
    }
    
    @Test
    fun `updateTranscription should update text and confidence`() {
        // Given: A transcription with confidence
        val text = "Hello Echo"
        val confidence = 0.95f
        
        // When: Transcription is updated
        viewModel.updateTranscription(text, confidence)
        
        // Then: State should reflect the transcription
        val state = viewModel.state.value
        assertEquals(text, state.lastTranscription)
        assertEquals(confidence, state.confidenceLevel)
    }
    
    @Test
    fun `updateSurpriseLevel should update surprise metric`() {
        // Given: A surprise level
        val surpriseLevel = 0.73f
        
        // When: Surprise level is updated
        viewModel.updateSurpriseLevel(surpriseLevel)
        
        // Then: State should reflect the surprise level
        val state = viewModel.state.value
        assertEquals(surpriseLevel, state.surpriseLevel)
    }
}