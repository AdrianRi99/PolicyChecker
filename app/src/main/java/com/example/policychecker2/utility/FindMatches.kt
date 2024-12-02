package com.example.policychecker2.utility

import com.example.policychecker2.db.models.terms.CustomTermsBaseClass
import com.example.policychecker2.db.models.terms.TermsBaseClass

class FindMatches (var content : String){


    private var resultsFound = mutableListOf<CustomTermsBaseClass>()


    fun findMatchesInTextCustomTerms(terms: List<CustomTermsBaseClass>) : MutableList<CustomTermsBaseClass> {

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

        return resultsFound
    }

    fun performAdvancedSearchCustomTerms(advancedTerms: List<CustomTermsBaseClass>) {


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

//        return resultsFound
    }



}