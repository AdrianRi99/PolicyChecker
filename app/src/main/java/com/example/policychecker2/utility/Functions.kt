package com.example.policychecker2.utility

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.viewModels
import com.example.policychecker2.db.models.terms.de.KaufvertragDe
import com.example.policychecker2.db.models.terms.de.KaufvertragDeAdvanced
import com.example.policychecker2.db.models.terms.de.MietvertragDe
import com.example.policychecker2.db.models.terms.de.MietvertragDeAdvanced
import com.example.policychecker2.db.models.terms.eng.MietvertragEng
import com.example.policychecker2.db.models.terms.eng.MietvertragEngAdvanced
import com.example.policychecker2.ui.viewModels.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import java.io.File


object Functions {

    //HomFragment
    fun getPdfFileName(context: Context, selectedPdf: Uri?) : String {

        //get the name of the pdf file
        val uriString = selectedPdf.toString()
        val file = File(uriString)
        var pdfFileTitle : String? = null

        if(uriString.startsWith("content://")) {
            var cursor : Cursor? = null
            try {
                cursor = context.contentResolver?.query(selectedPdf!!, null, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    pdfFileTitle = if (columnIndex != -1) {
                        cursor.getString(columnIndex)
                    } else {
                        "FileTitle not found"
                    }
                }
            } finally {
                cursor?.close()
            }
        } else if(uriString.startsWith("file://")) {
            pdfFileTitle = file.name
        }

        Log.d("FileName", pdfFileTitle.toString())

        return  pdfFileTitle.toString()
    }

}