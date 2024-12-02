package com.example.policychecker2.adapters

import android.graphics.Color
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.example.policychecker2.R
import com.example.policychecker2.databinding.FragmentResultsBinding
import com.example.policychecker2.db.models.terms.TermsBaseClass
import com.google.android.material.card.MaterialCardView

class ResultsLayout(
    private val resultsFound: MutableList<TermsBaseClass>,
    private var binding: FragmentResultsBinding,
    private val fragmentActivity: FragmentActivity,
    private val buttonShowResultInTextInterface: ButtonShowResultInTextInterface
) {


    fun generateLayout() {

        var termsType1Counter = 0
        var termsType2Counter = 0
        var termsType3Counter = 0

        val resultsFoundGroupedMap = resultsFound.groupBy { it.item }

        resultsFoundGroupedMap.forEach { resultsFoundGrouped ->

            val result = resultsFoundGrouped.value[0]

            when (result.type) {
                1 -> {
                    addViewToLinearLayoutAndBindIt(
                        R.layout.row_item_results,
                        binding.category1,
                        resultsFoundGrouped
                    )
                    termsType1Counter++

                }
                2 -> {
                    addViewToLinearLayoutAndBindIt(
                        R.layout.row_item_results,
                        binding.category2,
                        resultsFoundGrouped
                    )
                    termsType2Counter++
                }
                3 -> {
                    addViewToLinearLayoutAndBindIt(
                        R.layout.row_item_results,
                        binding.category3,
                        resultsFoundGrouped
                    )
                    termsType3Counter++
                }
            }
        }

        addOItemsFoundIfNeeded(termsType1Counter, termsType2Counter, termsType3Counter)
    }

    private fun addOItemsFoundIfNeeded(
        termsType1Counter: Int,
        termsType2Counter: Int,
        termsType3Counter: Int
    ) {

        if (termsType1Counter == 0) {
            val viewItem: View =
                fragmentActivity.layoutInflater.inflate(
                    R.layout.layout_0_items,
                    binding.category1,
                    false
                )

            val tv0TermsFound = viewItem.findViewById<TextView>(R.id.tv0TermsFound)
            tv0TermsFound.text = "No Basic Terms found"

            binding.category1.addView(viewItem)
        }
        if (termsType2Counter == 0) {
            val viewItem: View =
                fragmentActivity.layoutInflater.inflate(
                    R.layout.layout_0_items,
                    binding.category2,
                    false
                )

            val tv0TermsFound = viewItem.findViewById<TextView>(R.id.tv0TermsFound)
            tv0TermsFound.text = "No Dangerous Terms found"

            binding.category2.addView(viewItem)
        }
        if (termsType3Counter == 0) {
            val viewItem: View =
                fragmentActivity.layoutInflater.inflate(
                    R.layout.layout_0_items,
                    binding.category3,
                    false
                )

            val tv0TermsFound = viewItem.findViewById<TextView>(R.id.tv0TermsFound)
            tv0TermsFound.text = "No Invalid Terms found"

            binding.category3.addView(viewItem)
        }
    }

    private fun addViewToLinearLayoutAndBindIt(
        view: Int,
        rootLayout: LinearLayout,
        resultsFoundGrouped: Map.Entry<String, List<TermsBaseClass>>
    ) {
        val viewItem: View =
            fragmentActivity.layoutInflater.inflate(view, rootLayout, false)

        val dangerousTerm = viewItem.findViewById<TextView>(R.id.tvDangerousTerm)
        val termEasyTranslation = viewItem.findViewById<TextView>(R.id.tvTermEasyTranslation)
        val background: MaterialCardView = viewItem.findViewById(R.id.row_item_results)
        val btnSearchInText = viewItem.findViewById<Button>(R.id.btnSearchInText)
        val expandedLayout = viewItem.findViewById<ConstraintLayout>(R.id.expandedLayout)
        val icDropDown = viewItem.findViewById<ImageView>(R.id.icDropDown)
        val spinnerSelectOccurrence = viewItem.findViewById<Spinner>(R.id.spinnerResultItem)

        val result = resultsFoundGrouped.value[0]

        var resultTitle = ""

        resultsFoundGrouped.value.distinctBy {
            it.term
        }.forEach {
            resultTitle += "${it.term} / "
        }

        //resultsTitle remove Absatz wenn vorhanden with : .replace("\n", "")

        if(resultTitle.contains("\n")) {
            dangerousTerm.text = resultTitle.substringBeforeLast("/ ").replace("\n", "")
        } else {
            dangerousTerm.text = resultTitle.substringBeforeLast("/ ")
        }

        termEasyTranslation.text = result.termDescription

        if (result.type == 1) {
            background.strokeColor = Color.parseColor("#70B0FF")
        } else if (result.type == 2) {
            background.strokeColor = Color.parseColor("#F86A80")
        } else if (result.type == 3) {
            background.strokeColor = Color.parseColor("#FFBB86FC")
        }

        val spinnerSelectedOccurrencesItems = mutableListOf<String>()

        resultsFoundGrouped.value.forEachIndexed() { index, _ ->
            spinnerSelectedOccurrencesItems.add((index + 1).toString())
        }

        if(spinnerSelectedOccurrencesItems.size == 1) {
            spinnerSelectOccurrence.visibility = View.GONE
        }


        val arrayAdapter = ArrayAdapter(
            fragmentActivity,
            android.R.layout.simple_spinner_item,
            spinnerSelectedOccurrencesItems
        )
        spinnerSelectOccurrence.adapter = arrayAdapter

        var occurrenceToSearch = 0

        spinnerSelectOccurrence.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    occurrenceToSearch = position

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }


        btnSearchInText.setOnClickListener {
            buttonShowResultInTextInterface.onButtonClick(resultsFoundGrouped.value[occurrenceToSearch])

            Toast.makeText(
                fragmentActivity,
                "Occurrence number ${occurrenceToSearch + 1} searched",
                Toast.LENGTH_LONG
            ).show()

        }

        var isVisible = result.visibility   //as an Int -> 0 and 1
        expandAndShrinkResultItem(isVisible, icDropDown, expandedLayout)

        viewItem.setOnClickListener {
            isVisible = if (isVisible == 0) 1 else 0
            expandAndShrinkResultItem(isVisible, icDropDown, expandedLayout)
        }

        rootLayout.addView(viewItem)
    }

    private fun expandAndShrinkResultItem(
        isVisible: Int,
        icDropDown: ImageView,
        expandedLayout: ConstraintLayout
    ) {
        if (isVisible == 1) {
            icDropDown.visibility = View.GONE
            expandedLayout.visibility = View.VISIBLE
        } else {
            icDropDown.visibility = View.VISIBLE
            expandedLayout.visibility = View.GONE
        }
    }
}


interface ButtonShowResultInTextInterface {
    //creating a method for click action on recycler view item for updating it.
    fun onButtonClick(term: TermsBaseClass)
}
