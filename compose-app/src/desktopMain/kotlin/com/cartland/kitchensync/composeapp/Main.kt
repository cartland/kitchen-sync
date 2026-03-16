package com.cartland.kitchensync.composeapp

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() =
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Kitchen Sync",
        ) {
            App()
        }
    }
