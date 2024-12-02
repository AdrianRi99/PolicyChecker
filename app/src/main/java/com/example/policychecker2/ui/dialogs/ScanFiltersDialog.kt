package com.example.policychecker2.ui.dialogs

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import com.example.policychecker2.R
import com.example.policychecker2.ui.viewModels.ViewModel
import com.example.policychecker2.utility.Download
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScanFiltersDialog (private val scanFiltersDialogListener: ScanFiltersDialogListener) : AppCompatDialogFragment() {

    private lateinit var spinnerLanguage : Spinner
    private lateinit var spinnerFileType : Spinner
    private lateinit var icLanguageDownload : ImageView

    private val viewModel: ViewModel by viewModels()
    lateinit var downloadObject: Download



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.layout_scanfilters_dialog, null)

        downloadObject = Download(viewModel)

        spinnerLanguage = view.findViewById(R.id.spinnerLanguage)
        spinnerFileType = view.findViewById(R.id.spinnerFileType)

        icLanguageDownload = view.findViewById(R.id.icLanguageDownload)


        builder
            .setView(view)
            .setTitle("Scan Filters")
            .setNegativeButton("cancel", null)
            .setPositiveButton("apply & scan", null)

        val dialog = builder.create()

        icLanguageDownload.setOnClickListener {
            Log.d("Bad", "Test")
            val spinnerLanguage = spinnerLanguage.selectedItem.toString()
            downloadObject.downloadLanguagePackage(spinnerLanguage)
            icLanguageDownload.visibility = View.INVISIBLE
        }

        spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: android.view.View?,
                position: Int,
                id: Long
            ) {
//                val selectedLanguage = parent?.getItemAtPosition(position).toString()
//                Log.d("Spinner", "Ausgewählte Sprache: $selectedLanguage")
                icLanguageDownload.visibility = View.INVISIBLE
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE) as Button
            positiveButton.setOnClickListener {

                val spinnerFileType = spinnerFileType.selectedItem.toString()
                val spinnerLanguage = spinnerLanguage.selectedItem.toString()

//                if(spinnerLanguage == "Deutsch" && viewModel.getMietvertragDeTerms().isEmpty()){
//                    Toast.makeText(requireActivity(), "Language Packet 'Deutsch' noch nicht heruntergeladen - klicke den Download-Button", Toast.LENGTH_SHORT).show()
//                    icLanguageDownload.visibility = View.VISIBLE
//                } else if(spinnerLanguage == "English" && viewModel.getMietvertragEngTerms().isEmpty()) {
//                    Toast.makeText(requireActivity(), "Language Packet 'English' noch nicht heruntergeladen - klicke den Download-Button", Toast.LENGTH_SHORT).show()
//                    icLanguageDownload.visibility = View.VISIBLE
//                } else {
//                    scanFiltersDialogListener.scanFileAndNavigate(spinnerFileType, spinnerLanguage)
//                    dialog.dismiss()
//                }
                dialog.dismiss()
                scanFiltersDialogListener.scanFileAndNavigate(spinnerFileType, spinnerLanguage)
            }
        }

        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#4E62BE"));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#4E62BE"));

        return dialog
    }


    interface ScanFiltersDialogListener {
        fun scanFileAndNavigate(spinnerFileType: String, spinnerLanguage: String)
    }
}