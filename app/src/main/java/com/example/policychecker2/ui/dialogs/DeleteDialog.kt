package com.example.policychecker2.ui.dialogs

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.policychecker2.db.models.Files

class DeleteDialog (private val deleteDialogListener: DeleteDialogListener, val file: Files) : AppCompatDialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())

        builder
            .setView(view)
            .setTitle("Delete File")
            .setMessage("Do you want to delete file '${file.fileTitle}' ?")
            .setNegativeButton("CANCEL", null)
            .setPositiveButton("OK", null)

        val dialog = builder.create()
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE) as Button
            positiveButton.setOnClickListener {
                deleteDialogListener.deleteFile(file)
                Toast.makeText(requireContext(), "${file.fileTitle} deleted", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }
        }

        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#4E62BE"));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#4E62BE"));

        return dialog
    }


    interface DeleteDialogListener {
        fun deleteFile(file : Files)
    }
}