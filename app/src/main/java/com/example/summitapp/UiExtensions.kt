package com.example.summitapp

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.showMessage(
    title: String,
    message: String
) {
    if (!isFinishing && !isDestroyed) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .create()
            .show()
    }
}