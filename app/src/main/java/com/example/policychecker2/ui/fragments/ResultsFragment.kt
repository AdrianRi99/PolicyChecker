package com.example.policychecker2.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.marginEnd
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.policychecker2.R
import com.example.policychecker2.adapters.ButtonShowResultInTextInterface
import com.example.policychecker2.adapters.ResultsLayout
import com.example.policychecker2.databinding.FragmentResultsBinding
import com.example.policychecker2.db.models.terms.TermsBaseClass
import com.example.policychecker2.db.models.terms.de.MietvertragDe
import com.example.policychecker2.db.models.Files
import com.example.policychecker2.db.models.terms.CustomTermsBaseClass
import com.example.policychecker2.db.models.terms.de.MietvertragDeAdvanced
import com.example.policychecker2.ui.dialogs.SaveDialog
import com.example.policychecker2.ui.viewModels.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class ResultsFragment : Fragment(R.layout.fragment_results), ButtonShowResultInTextInterface,
    SaveDialog.SaveDialogListener {

    private val viewModal: ViewModel by viewModels()
    private lateinit var binding: FragmentResultsBinding
    private val args: ResultsFragmentArgs by navArgs()

    private var resultsFound = mutableListOf<TermsBaseClass>()
    var content = ""

//    var customTermsFoundInText = mutableListOf<CustomTermsBaseClass>()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentResultsBinding.bind(view)


//        resultsFound =
//            mutableListOf() //has to made empty when returning from Textfragment, so no multiple entries

        resultsFound = args.resultsFound.toMutableList()
        content = args.content


        if (args.type == "saved"){
            binding.btnSaveFile.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                marginEnd = 0
            }
            binding.btnSaveFile.visibility = View.GONE
        } else if (args.type == "result"){

        }


//        if(resultsFound.isNotEmpty()){
//            resultsFound = resultsFound.sortedBy {
//                it.indexRangeInText.startIndex()
//            } as MutableList<TermsBaseClass>
//        }

        val resultsLayout = ResultsLayout(resultsFound, binding, requireActivity(), this)
        resultsLayout.generateLayout()

        //ok
        binding.btnOpenText.setOnClickListener {
            val action = ResultsFragmentDirections.actionResultsFragmentToShowTextFragment(
                content,
                resultsFound.toTypedArray(),
                args.pdfFileTitle
            )
            findNavController().navigate(action)
        }


        binding.btnSaveFile.setOnClickListener {
            openDialog()
        }

    }


    //Ok
    private fun openDialog() {
        val saveDialog = SaveDialog(this, args.pdfFileTitle)
        saveDialog.show(requireActivity().supportFragmentManager, "save dialog")
    }



    //Ok
    override fun saveFileTitle(fileTitle: String) {
        val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
        val currentDateAndTime: String = sdf.format(Date())
        val fileToSave =
            Files(fileTitle, currentDateAndTime, content, resultsFound as MutableList<MietvertragDe>)
        viewModal.addFile(fileToSave)

        findNavController().navigate(
            ResultsFragmentDirections.actionResultsFragmentToHomeFragment2(
                scrollToBottom = true
            )
        )

        Toast.makeText(requireContext(), "File saved - hold item for options", Toast.LENGTH_LONG)
            .show()
    }

    override fun onButtonClick(term: TermsBaseClass) {
        val action = ResultsFragmentDirections.actionResultsFragmentToShowTextFragment(
            content = content,
            resultsFound = resultsFound.toTypedArray(),
            pdfFileTitle = args.pdfFileTitle,
            true,
            scrollTerm = term
        )
        findNavController().navigate(action)
    }
}