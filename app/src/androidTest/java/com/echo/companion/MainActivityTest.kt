package com.echo.companion

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun useAppContext() {
        // Context of the app under test
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.echo.companion.debug", appContext.packageName)
    }
    
    @Test
    fun echoLivesMessageIsDisplayed() {
        // Given: Main screen is displayed
        composeTestRule.setContent {
            EchoMainScreen()
        }
        
        // Then: "Echo Lives!" message should be visible
        composeTestRule
            .onNodeWithText("Echo Lives! 🎯")
            .assertIsDisplayed()
    }
    
    @Test
    fun echoCompanionTitleIsDisplayed() {
        // Given: Main screen is displayed
        composeTestRule.setContent {
            EchoMainScreen()
        }
        
        // Then: App title should be visible
        composeTestRule
            .onNodeWithText("Echo Companion")
            .assertIsDisplayed()
    }
    
    @Test
    fun startRecordingButtonIsDisplayed() {
        // Given: Main screen is displayed
        composeTestRule.setContent {
            EchoMainScreen()
        }
        
        // Then: Start Recording button should be visible
        composeTestRule
            .onNodeWithText("Start Recording")
            .assertIsDisplayed()
    }
    
    @Test
    fun settingsButtonIsDisplayed() {
        // Given: Main screen is displayed
        composeTestRule.setContent {
            EchoMainScreen()
        }
        
        // Then: Settings button should be visible
        composeTestRule
            .onNodeWithText("Settings")
            .assertIsDisplayed()
    }
    
    @Test
    fun idleStatusIsShownInitially() {
        // Given: Main screen is displayed
        composeTestRule.setContent {
            EchoMainScreen()
        }
        
        // Then: Idle status should be shown
        composeTestRule
            .onNodeWithText("○ Idle")
            .assertIsDisplayed()
    }
    
    @Test
    fun permissionsCardIsDisplayed() {
        // Given: Main screen is displayed
        composeTestRule.setContent {
            EchoMainScreen()
        }
        
        // Then: Permissions status card should be visible
        composeTestRule
            .onNodeWithText("Permissions Status")
            .assertIsDisplayed()
    }
    
    @Test
    fun audioPermissionRowIsDisplayed() {
        // Given: Main screen is displayed
        composeTestRule.setContent {
            EchoMainScreen()
        }
        
        // Then: Audio Recording permission row should be visible
        composeTestRule
            .onNodeWithText("Audio Recording")
            .assertIsDisplayed()
    }
    
    @Test
    fun appInfoIsDisplayed() {
        // Given: Main screen is displayed
        composeTestRule.setContent {
            EchoMainScreen()
        }
        
        // Then: App info should be visible
        composeTestRule
            .onNodeWithText("Echo is your personal on-device AI companion.", substring = true)
            .assertIsDisplayed()
    }
}