package com.example.policychecker2.ui.dialogs

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.viewModels
import com.example.policychecker2.R
import com.example.policychecker2.ui.viewModels.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LanguagePackageDialog (private val languagePackageDialogListener: LanguagePackageDialogListener) : AppCompatDialogFragment() {

    private lateinit var spinnerLanguage : Spinner
    private lateinit var icLanguageDownloaded : ImageView
    private val viewModel: ViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.layout_language_packages_dialog, null)

        spinnerLanguage = view.findViewById(R.id.spinnerLanguage)
        icLanguageDownloaded = view.findViewById(R.id.icLanguageDownloaded)



        spinnerLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedLanguage = parent?.getItemAtPosition(position).toString()

                if (selectedLanguage == "Deutsch"){
                    if(viewModel.getMietvertragDeTerms().isEmpty()) {
                        icLanguageDownloaded.visibility = View.GONE
                    } else {
                        icLanguageDownloaded.visibility = View.VISIBLE
                    }
                } else if (selectedLanguage == "English") {
                    if(viewModel.getMietvertragEngTerms().isEmpty()) {
                        icLanguageDownloaded.visibility = View.GONE
                    }  else {
                        icLanguageDownloaded.visibility = View.VISIBLE
                    }
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        builder
            .setView(view)
            .setTitle("Download a language package")
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Download", null)

        val dialog = builder.create()
        dialog.setOnShowListener {
            val positiveButton = dialog.getButton(Dialog.BUTTON_POSITIVE) as Button
            positiveButton.setOnClickListener {

                val spinnerLanguage = spinnerLanguage.selectedItem.toString()

//                if(spinnerLanguage == "Deutsch" && viewModel.getMietvertragDeTerms().isNotEmpty()){
//                    Toast.makeText(requireActivity(), "Language Packet 'Deutsch' bereits heruntergeladen", Toast.LENGTH_SHORT).show()
//                } else if(spinnerLanguage == "English" && viewModel.getMietvertragEngTerms().isNotEmpty()){
//                    Toast.makeText(requireActivity(), "Language Packet 'English' bereits heruntergeladen", Toast.LENGTH_SHORT).show()
//                } else {
//                    languagePackageDialogListener.downloadLanguagePackage(spinnerLanguage)
//                    dialog.dismiss()
//                }

                languagePackageDialogListener.downloadLanguagePackage(spinnerLanguage)


            }
        }

        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#4E62BE"));
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#4E62BE"));

        return dialog
    }


    interface LanguagePackageDialogListener {
        fun downloadLanguagePackage(spinnerLanguage: String)
    }
}