//package com.example.policychecker2.ui.fragments
//
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.View
//import android.widget.Toast
//import androidx.fragment.app.viewModels
//import androidx.navigation.fragment.findNavController
//import androidx.navigation.fragment.navArgs
//import com.example.policychecker2.R
//import com.example.policychecker2.adapters.*
//import com.example.policychecker2.databinding.FragmentSavedResultsBinding
//import com.example.policychecker2.db.models.terms.CustomTerms
//import com.example.policychecker2.db.models.terms.CustomTermsAdvanced
//import com.example.policychecker2.db.models.terms.CustomTermsBaseClass
//import com.example.policychecker2.db.models.terms.TermsBaseClass
//import com.example.policychecker2.ui.viewModels.ViewModel
//import com.example.policychecker2.utility.FindMatches
//import dagger.hilt.android.AndroidEntryPoint
//
//
//@AndroidEntryPoint
//class SavedResultsFragment : Fragment(R.layout.fragment_saved_results),  ButtonShowSavedResultInTextInterface{
//
//
//
//    private lateinit var binding: FragmentSavedResultsBinding
//
//    private val args:  SavedResultsFragmentArgs by navArgs()
//
//    var content = ""
//    var resultsFound = mutableListOf<TermsBaseClass>()
//
////    var customTerms = mutableListOf<CustomTerms>()
////    var customTermsAdvanced = mutableListOf<CustomTermsAdvanced>()
////
////    var customTermsFoundInText = mutableListOf<CustomTermsBaseClass>()
//
////    private val viewModal: ViewModel by viewModels()
////    lateinit var terms : List<TermsBaseClass>
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentSavedResultsBinding.bind(view)
//
//        resultsFound = args.resultsFound.toMutableList()
//
////        terms = viewModal.getMietvertragDeTerms()  //here has to be the right language and contract type be loaded
//
//        content = args.content
//
////        val findMatches = FindMatches(content)
//
//        val savedResultsLayout = SavedResultsLayout(resultsFound, binding, requireActivity(), this)
//        savedResultsLayout.generateLayout()
//
//        binding.btnOpenText.setOnClickListener {
//            val action = SavedResultsFragmentDirections.actionSavedResultsFragmentToShowTextFragment(content, resultsFound.toTypedArray(), args.pdfFileTitle)  //zweimal resultsFound da zweites nur dummy
//            findNavController().navigate(action)
//        }
//
////        binding.btnAddCustomTerms.setOnClickListener {
//////            Log.d("new", binding.editTextOwnTerms.text.toString())
////
////            customTerms = mutableListOf() //zurücksetzen damit listeinträge nicht doppelt
////
////            val term = binding.editTextOwnTerms.text.toString()
////
////            if(term.contains(",")) {
////                customTermsAdvanced.add(CustomTermsAdvanced(term))
//////                logCustomTermsAdvanced()
////            } else {
//////                customTerms.add(CustomTerms(term))
////                checkIfCustomTermAlreadyAddedElseAddToList(term)
//////                logCustomTerms()
////            }
////        }
//
////        binding.btnSearchCustomTerms.setOnClickListener {
////            customTermsFoundInText = findMatches.findMatchesInTextCustomTerms(customTerms)
//////            customTermsFoundInText = findMatches.performAdvancedSearchCustomTerms(customTermsAdvanced)
////            logCustomTermsFoundInText()
////            savedResultsLayout.addCustomTermsToLayout( R.layout.row_item_custom_result, binding.category4, customTermsFoundInText)
////
////        }
//
//
//
//
//    }
//
////    private fun checkIfCustomTermAlreadyAddedElseAddToList(term: String) {
////        for (customTerm in customTerms) {
////            if(term == customTerm.term) {
////                Toast.makeText(requireActivity(), "Term already added!", Toast.LENGTH_LONG).show()
////                return
////            }
////        }
////
////        customTerms.add(CustomTerms(term))
////    }
////
////    private fun logCustomTermsFoundInText() {
////
////
////        customTermsFoundInText.forEach {
////            Log.d("yii", it.term)
////        }
////    }
////
////    private fun logCustomTerms() {
////
////        Log.d("new", "customTerms")
////        Log.d("new", "")
////
////        customTerms.forEach {
////            Log.d("new", it.term)
////            Log.d("new", "")
////
////        }
////    }
////
////    private fun logCustomTermsAdvanced() {
////
////        Log.d("new", "customTermsAdvanced")
////        Log.d("new", "")
////
////        customTermsAdvanced.forEach {
////
////            Log.d("new", it.term)
////            Log.d("new", "")
////
////        }
////    }
//
//
//    override fun onButtonClick(term: TermsBaseClass) {
//        val action = SavedResultsFragmentDirections.actionSavedResultsFragmentToShowTextFragment(content, resultsFound.toTypedArray(), args.pdfFileTitle,true,
//            term
//        )
//        findNavController().navigate(action)
//    }
//
////    override fun onButtonClick(term: CustomTermsBaseClass) {
////        val action = SavedResultsFragmentDirections.actionSavedResultsFragmentToShowTextFragment(content, resultsFound.toTypedArray(), args.pdfFileTitle,true,
////            term
////        )
////        findNavController().navigate(action)
////    }
//}