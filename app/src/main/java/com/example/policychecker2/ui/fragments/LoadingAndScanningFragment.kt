package com.example.policychecker2.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.policychecker2.R
import com.example.policychecker2.db.models.terms.TermsBaseClass
import com.example.policychecker2.ui.viewModels.ViewModel
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll


@AndroidEntryPoint
class LoadingAndScanningFragment : Fragment(R.layout.fragment_loading_and_scanning) {

    private val viewModal: ViewModel by viewModels()
    private val args: LoadingAndScanningFragmentArgs by navArgs()

    lateinit var terms: List<TermsBaseClass>
    lateinit var advancedTerms: List<TermsBaseClass>

    var content = ""

    private var resultsFound = mutableListOf<TermsBaseClass>()

//    private val delayInMillis: Long = 2000 // 2 Sekunden
//
//    private var countdownTimer: CountDownTimer? = null


//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Initialisiere das ViewModel, wenn es noch nicht initialisiert wurde oder es zuvor auf null gesetzt wurde.
//        if (viewModal == null) {
//            viewModal = ViewModelProvider(this)[ViewModel::class.java]
//        }
//    }

    override fun onResume() {
        super.onResume()


        lifecycleScope.launch(Dispatchers.Default) {
            // Berechnungen oder CPU-intensive Aufgaben hier ausführen

            readText(args.uri)
            checkFileType()



            // Wenn die Berechnung abgeschlossen ist, navigiere zum nächsten Fragment
            withContext(Dispatchers.Main) {
                val action = LoadingAndScanningFragmentDirections.actionLoadingAndScanningFragmentToResultsFragment(content, resultsFound.toTypedArray(), args.pdfFileTitle, "result")
                findNavController().navigate(action)
            }
        }

//        countdownTimer = object : CountDownTimer(delayInMillis, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                // Nichts zu tun - hier könnten Sie eine Aktualisierung der UI durchführen, wenn nötig
//            }
//
//            override fun onFinish() {
//                // Führen Sie hier den Code Ihrer Aktion aus
//
//
//            lifecycleScope.launch(Dispatchers.Default) {
//            // Berechnungen oder CPU-intensive Aufgaben hier ausführen
//
//                readText(args.uri)
//                checkFileType()
//
//            // Wenn die Berechnung abgeschlossen ist, navigiere zum nächsten Fragment
//            withContext(Dispatchers.Main) {
//                val action = LoadingAndScanningFragmentDirections.actionLoadingAndScanningFragmentToResultsFragment(content, resultsFound.toTypedArray(), args.pdfFileTitle)
//                findNavController().navigate(action)
//            }
//            }
//
//            }
//        }.start()


    }

//    override fun onPause() {
//        super.onPause()
//        Log.d("Oha", "gut")
//        stopCountdown()
//        viewModal = null
//    }

//    private fun stopCountdown() {
//        countdownTimer?.cancel()
//        countdownTimer = null
//    }

    private fun readText(uri: Uri?){
        val inputStream = requireActivity().contentResolver?.openInputStream(uri!!)  //not safe with !!

        PDDocument.load(inputStream).use { pdfDocument ->
            if (!pdfDocument.isEncrypted) {
                content = PDFTextStripper().getText(pdfDocument)
            }
        }
    }



    private suspend fun checkFileType() {

        val fileTypeConcat = args.spinnerFileType + args.spinnerLanguage

        Log.d("Yap", fileTypeConcat)

        //bei fertigstellung testen, wie lange die unterschiedlichen Wege zum Download brauchen

        when(fileTypeConcat) {
            "MietvertragDeutsch" -> {
                terms = viewModal.getMietvertragDeTerms()
                advancedTerms = viewModal.getMietvertragDeAdvancedTerms()
            }
            "MietvertragEnglish" -> {
                terms = viewModal.getMietvertragEngTerms()
                advancedTerms = viewModal.getMietvertragEngAdvancedTerms()
            }
            "KaufvertragDeutsch" -> {
                terms = viewModal.getKaufvertragDeTerms()
                advancedTerms = viewModal.getKaufvertragDeAdvancedTerms()
            }
        }

//        findMatchesInText(terms)
//        performAdvancedSearch(advancedTerms)
//        organizeResults()



//         Erstellen Sie einen neuen Coroutine-Scope
        coroutineScope {
            // Verwenden Sie async, um findMatchesInText und performAdvancedSearch parallel auszuführen
            val findMatchesJob = async { findMatchesInText(terms) }
            val performAdvancedSearchJob = async { performAdvancedSearch(advancedTerms) }

            // Warten Sie auf das Ende beider Jobs
            awaitAll(findMatchesJob, performAdvancedSearchJob)

            // Jetzt können Sie organizeResults aufrufen
            organizeResults()
        }

//        if (args.spinnerFileType == "Mietvertrag") {
//            if(args.spinnerLanguage == "Deutsch") {
//                terms = viewModal.getMietvertragDeTerms()
//                advancedTerms = viewModal.getMietvertragDeAdvancedTerms()
//            } else if(args.spinnerLanguage == "English") {
//                terms = viewModal.getMietvertragEngTerms()
//                advancedTerms = viewModal.getMietvertragEngAdvancedTerms()
//            }
//
//            findMatchesInText(terms)
//            performAdvancedSearch(advancedTerms)
//
//        } else if (args.spinnerFileType == "Kaufvertrag") {
//            if (args.spinnerLanguage == "Deutsch") {
//                terms = viewModal.getKaufvertragDeTerms()
//                advancedTerms = viewModal.getKaufvertragDeAdvancedTerms()
//            }
//
//            findMatchesInText(terms)
//            performAdvancedSearch(advancedTerms)
//
//        }




    }




    private fun findMatchesInText(terms: List<TermsBaseClass>) {

//        content = args.content

        for (term in terms) {
            val regex =
                Regex("${term.term}(?![A-Za-z])")   //negative look ahead - so something like Miete in Mieter does not get found
            val matches = regex.findAll(content)
            val listOfRanges = matches.map { it.range.toString() }

            listOfRanges.forEach { range ->

                val clonedTerm = term.clone()
                clonedTerm.indexRangeInText = range
                resultsFound.add(clonedTerm)

            }

        }
    }


    private fun performAdvancedSearch(advancedTerms: List<TermsBaseClass>) {


//        content = args.content

        val textSplitted = content.split(".")
        var trueCounter = 0

        textSplitted.forEach { line ->

            var singleLine = line

            if(singleLine.contains("\n")) {
                singleLine = singleLine.replace("\n", "")
            }

            advancedTerms.forEach { advancedTerm ->
                if (advancedTerm.term.contains(",")) {
                    val advancedTermSplitted = advancedTerm.term.split(",")
                    advancedTermSplitted.forEach { word ->
                        if (singleLine.contains(word)) {
                            trueCounter++
                        }
                    }
                    if (trueCounter >= (advancedTermSplitted.size * 0.75)) {


                        val clonedTerm = advancedTerm.clone()
                        clonedTerm.term = singleLine
                        resultsFound.add(clonedTerm)

                    }
                    trueCounter = 0
                } else {
                    if (singleLine.contains(advancedTerm.term)) {
                        val clonedTerm = advancedTerm.clone()
                        clonedTerm.term = singleLine
                        resultsFound.add(clonedTerm)
                    }
                }
            }

        }
//
//        val resultsFoundGrouped = resultsFound.groupBy {
//            it.term
//        }
//
//
//        resultsFound = mutableListOf()
//
//        if(content.contains("\n")) {
//            content = content.replace("\n", "")
//        }
//
//        resultsFoundGrouped.forEach { (term, results) ->
//
//            val regex = Regex("${term}(?![A-Za-z])")   //negative look ahead - so something like Miete in Mieter does not get found
//            val matches = regex.findAll(content)
//
//
//            val listOfRanges = matches.map { it.range.toString() }.toList()
//
//
//            results.forEachIndexed { index, result ->
//                result.indexRangeInText = listOfRanges[index]
//                resultsFound.add(result)
//            }
//
//
//        }
//
//
//        //nach Ende der Advanced Search - weis nicht genau warum - wsl wegen reihenfolge des spezifischen begriff suchens
//        if(resultsFound.isNotEmpty()){
//            resultsFound = resultsFound.sortedBy {
//                it.indexRangeInText.startIndex()
//            } as MutableList<TermsBaseClass>
//        }
    }


    private fun organizeResults() {
        val resultsFoundGrouped = resultsFound.groupBy {
            it.term
        }


        resultsFound = mutableListOf()

        if(content.contains("\n")) {
            content = content.replace("\n", "")
        }

        resultsFoundGrouped.forEach { (term, results) ->

            val regex = Regex("${term}(?![A-Za-z])")   //negative look ahead - so something like Miete in Mieter does not get found
            val matches = regex.findAll(content)


            val listOfRanges = matches.map { it.range.toString() }.toList()


            results.forEachIndexed { index, result ->
                result.indexRangeInText = listOfRanges[index]
                resultsFound.add(result)
            }


        }


        //nach Ende der Advanced Search - weis nicht genau warum - wsl wegen reihenfolge des spezifischen begriff suchens
        if(resultsFound.isNotEmpty()){
            resultsFound = resultsFound.sortedBy {
                it.indexRangeInText.startIndex()
            } as MutableList<TermsBaseClass>
        }
    }
}