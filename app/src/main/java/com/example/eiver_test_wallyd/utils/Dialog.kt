package com.example.eiver_test_wallyd.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import com.example.eiver_test_wallyd.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineExceptionHandler

class Dialog {
    fun displayDialog(
        context: Context,
        title: String?,
        message: String?
    ): Dialog {
        val alert = MaterialAlertDialogBuilder(context)
        alert.setCancelable(false)
        alert.setMessage(message)
        alert.setTitle(title)
        alert.setPositiveButton(
            context.getString(R.string.Ok)
        ) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        return alert.create()
    }

    fun displayError(context: Context): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(context).setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }
    }
}