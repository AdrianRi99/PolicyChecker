package com.example.policychecker2.ui.fragments

import android.graphics.Color
import android.os.Bundle

import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.navArgs
import com.example.policychecker2.R
import com.example.policychecker2.databinding.FragmentShowTextBinding
import com.example.policychecker2.db.models.terms.CustomTermsBaseClass
import com.example.policychecker2.db.models.terms.de.MietvertragDe
import com.example.policychecker2.db.models.terms.TermsBaseClass
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ShowTextFragment : Fragment(R.layout.fragment_show_text) {

    private lateinit var binding: FragmentShowTextBinding
    private val args: ShowTextFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShowTextBinding.bind(view)

        val content = args.content
        val resultsFound = args.resultsFound

//        val customResultsFound = args.customResults

        binding.tvPdfTitle.text = args.pdfFileTitle

        //if it is true
        if (args.scrollTo) {
            scrollToTextPassageAndSetClickListeners(
                content,
                args.scrollTerm!!,
                resultsFound)
        } else {
            setTermsClickListener(content, resultsFound, 1)
        }

    }

    private fun setTermsClickListener(
        content: String,
        resultsFound: Array<TermsBaseClass>,
        type: Int
    ): SpannableString {

//        customResultsFound: Array<CustomTermsBaseClass>,

        val modifiedText = SpannableString(content)

        resultsFound.forEachIndexed { _, result ->
            val start = result.indexRangeInText.startIndex()
            val end = result.indexRangeInText.endIndex() + 1
            val clickableSpan: ClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    MaterialAlertDialogBuilder(requireContext())
                        .setMessage(result.termDescription)
                        .show()
                }

                override fun updateDrawState(ds: TextPaint) {
                    //für Schriftfarbe

//                    if (result.type == 1) {
//                        ds.color = ContextCompat.getColor(requireActivity(), R.color.colorBasicTerm)
//
//                    } else if (result.type == 2) {
//                        ds.color = ContextCompat.getColor(requireActivity(), R.color.colorDangerousTerm)
//                    } else if (result.type == 3) {
//                        ds.color = ContextCompat.getColor(requireActivity(), R.color.colorInvalidTerms)
//                    }
                }
            }

            modifiedText.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


            // Färbe den Hintergrund entsprechend des result.type
            val backgroundColor = when (result.type) {
                1 -> ContextCompat.getColor(requireActivity(), R.color.colorBasicTermLight)
                2 -> ContextCompat.getColor(requireActivity(), R.color.colorDangerousTermLight)
                3 -> ContextCompat.getColor(requireActivity(), R.color.colorInvalidTermsLight)
                else -> Color.TRANSPARENT // Standard: Transparenter Hintergrund, falls result.type unbekannt
            }

            // SpannableString anwenden, um den Hintergrund des Texts zu markieren
            modifiedText.setSpan(
                BackgroundColorSpan(backgroundColor),
                start,
                end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )


        }

//        customResultsFound.forEach { result ->
//            val start = result.indexRangeInText.startIndex()
//            val end = result.indexRangeInText.endIndex() + 1
//
//            val backgroundColor = ContextCompat.getColor(requireActivity(), R.color.colorCustomTerms)
//
//            modifiedText.setSpan(
//                BackgroundColorSpan(backgroundColor),
//                start,
//                end,
//                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//            )
//        }

        return if (type == 1) {
            binding.tvShowText.setText(modifiedText, TextView.BufferType.SPANNABLE)
            binding.tvShowText2.visibility = View.GONE
            binding.tvShowText.movementMethod = LinkMovementMethod.getInstance()

            modifiedText
        } else {
            modifiedText
        }
    }


    private fun scrollToTextPassageAndSetClickListeners(
        content: String,
        termToScroll: TermsBaseClass,
        resultsFound: Array<TermsBaseClass>) {
        //Step 1 - go to passage in text where term is found
        val firstPart = content.substring(0, termToScroll.indexRangeInText.startIndex())
        val secondPart = content.substring(termToScroll.indexRangeInText.startIndex())

        binding.tvShowText.text = firstPart
        binding.tvShowText2.visibility = View.VISIBLE
        binding.tvShowText2.text = secondPart
        binding.scrollView.post {
            binding.scrollView.scrollTo(0, binding.tvShowText2.top)
        }

        //Step 2 - find the right indexes of the resultTerms
        var (secondPartResults, firstPartResults) = resultsFound.partition { result -> result.indexRangeInText.startIndex() >= termToScroll.indexRangeInText.startIndex() }

        secondPartResults = secondPartResults.map {
            MietvertragDe(
                it.term,
                it.termDescription,
                it.type,
                it.item,
                it.indexRangeInText.calculateNewIndex(termToScroll.indexRangeInText.startIndex())
            )
        }

        //Step 3 - Set ClickListeners

        val firstPartSpan = setTermsClickListener(firstPart, firstPartResults.toTypedArray(), 2)
        val secondPartSpan = setTermsClickListener(secondPart, secondPartResults.toTypedArray(), 2)

        binding.tvShowText.setText(firstPartSpan, TextView.BufferType.SPANNABLE)
        binding.tvShowText2.setText(secondPartSpan, TextView.BufferType.SPANNABLE)
        binding.tvShowText.movementMethod = LinkMovementMethod.getInstance()
        binding.tvShowText2.movementMethod = LinkMovementMethod.getInstance()
    }

}

fun String.startIndex(): Int =
    substringBefore("..").toInt()

private fun String.endIndex(): Int =
    substringAfter("..").toInt()

private fun String.calculateNewIndex(oldStartIndex: Int): String =
    "${substringBefore("..").toInt() - oldStartIndex}..${substringAfter("..").toInt() - oldStartIndex}"


