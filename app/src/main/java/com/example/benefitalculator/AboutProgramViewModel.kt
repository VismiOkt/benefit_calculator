package com.example.benefitalculator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel


class AboutProgramViewModel : ViewModel() {

    fun copyToClipboard(context: Context, text: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(text, text)
        clipboardManager.setPrimaryClip(clip)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
            Toast.makeText(context, R.string.about_program_screen_copy, Toast.LENGTH_SHORT).show()

    }
}