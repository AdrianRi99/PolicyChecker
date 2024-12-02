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
import com.example.policychecker2.db.models.Files

class LoadingDialog () : AppCompatDialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.layout_loading_dialog, null)

        builder
            .setView(view)
            .setCancelable(false)


        val dialog = builder.create()

        dialog.show()

        return dialog
    }

}