package com.example.eiver_test_wallyd.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import com.example.eiver_test_wallyd.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
}