package com.example.policychecker2.utility

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
import com.google.firebase.database.*


//@AndroidEntryPoint
class Download (val viewModel: ViewModel) {

    //Firebase
    private val database = FirebaseDatabase.getInstance()
    lateinit var termsRef : DatabaseReference


    fun downloadLanguagePackage(spinnerLanguage: String) {

        termsRef = database.getReference("terms").child("What")
        termsRef.setValue(7)
        when(spinnerLanguage) {
            "Deutsch" ->  termsRef = database.getReference("termsDe")
            "English" ->  termsRef = database.getReference("termsEng")
        }

        termsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(termLanguage: DataSnapshot) {
                for (contractType in termLanguage.children) {


                    for(termType in contractType.children) { //termType -> Advanced or General


                        for(term in termType.children){
                            val termTitle = term.child("term").getValue(String::class.java)
                            val termDescription = term.child("termDescription").getValue(String::class.java)
                            val type = term.child("type").getValue(Int::class.java)
                            val item = term.child("item").getValue(String::class.java)
                            val indexRangeInText = term.child("indexRangeInText").getValue(String::class.java)

                            val termRoot = termType.key.toString()
                            val termTypeConcat = termsRef.key.toString() + contractType.key.toString()

                            when(termRoot){
                                "General" -> when(termTypeConcat){
                                    "termsDeKaufvertrag" -> viewModel.addKaufvertragDeTerms(KaufvertragDe(termTitle!!, termDescription!!, type!!, item!!, indexRangeInText!!))
                                    "termsDeMietvertrag" ->  viewModel.addMietvertragDeTerms(MietvertragDe(termTitle!!, termDescription!!, type!!, item!!, indexRangeInText!!))
                                    "termsEngMietvertrag" ->  viewModel.addMietvertragEngTerms(MietvertragEng(termTitle!!, termDescription!!, type!!, item!!))
                                }
                                "Advanced" -> when(termTypeConcat){
                                    "termsDeKaufvertrag" -> viewModel.addKaufvertragDeAdvancedTerms(
                                        KaufvertragDeAdvanced(termTitle!!, termDescription!!, type!!, item!!, indexRangeInText!!)
                                    )
                                    "termsDeMietvertrag" ->    viewModel.addMietvertragDeAdvancedTerms(
                                        MietvertragDeAdvanced(termTitle!!, termDescription!!, type!!, item!!, indexRangeInText!!)
                                    )
                                    "termsEngMietvertrag" ->  viewModel.addMietvertragEngAdvancedTerms(MietvertragEngAdvanced(termTitle!!, termDescription!!, type!!, item!!))
                                }
                            }

                        }

                    }



                }


            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

    }


}