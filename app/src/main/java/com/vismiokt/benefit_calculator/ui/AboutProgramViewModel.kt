package com.vismiokt.benefit_calculator.ui

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.vismiokt.benefit_calculator.R


class AboutProgramViewModel : ViewModel() {

    fun sendMail(context: Context) {
        val email = context.getString(R.string.about_program_screen_email)
        val subject = context.getString(R.string.about_program_screen_email_subject)
        val emailCopy = context.getString(R.string.about_program_screen_email_copy)
        try {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            val shareIntent = Intent.createChooser(intent, null)
            context.startActivity(shareIntent)
        } catch (e: ActivityNotFoundException) {
            copyToClipboard(context, email, emailCopy)
        } catch (t: Throwable) {
        }
    }

    private fun copyToClipboard(context: Context, text: String, textDialog: String) {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(text, text)
        clipboardManager.setPrimaryClip(clip)
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
            Toast.makeText(context, textDialog, Toast.LENGTH_SHORT).show()

    }
}