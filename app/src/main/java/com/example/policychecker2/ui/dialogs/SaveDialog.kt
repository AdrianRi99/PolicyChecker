package com.example.policychecker2.ui.dialogs

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.policychecker2.R

class SaveDialog (private val saveDialogListener: SaveDialogListener, private val pdfFileTitle : String) : AppCompatDialogFragment() {

    private lateinit var editTextPdfFileTitle : EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.layout_save_dialog, null)

        editTextPdfFileTitle = view.findViewById(R.id.editPdfFileTitle)
        editTextPdfFileTitle.setText(pdfFileTitle)

        builder
            .setView(view)
            .setTitle("Save File")
            .setNegativeButton("cancel", null)
            .setPositiveButton("save", null)

        val dialog = builder.create()
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE) as Button
            positiveButton.setOnClickListener {
                val fileTitle = editTextPdfFileTitle.text.toString()
                if(fileTitle.isBlank()){
                    Toast.makeText(requireActivity(), "Enter a file title", Toast.LENGTH_SHORT).show()
                } else {
                    saveDialogListener.saveFileTitle(fileTitle)
                    dialog.dismiss()
                }
            }
        }

        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#4E62BE"));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#4E62BE"));

        return dialog
    }


    interface SaveDialogListener {
        fun saveFileTitle(fileTitle : String)
    }
}