package com.example.policychecker2.ui.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.policychecker2.R
import com.example.policychecker2.databinding.FragmentShowPdfBinding
import com.example.policychecker2.db.models.terms.TermsBaseClass
import com.example.policychecker2.ui.dialogs.LoadingDialog
import com.example.policychecker2.ui.dialogs.ScanFiltersDialog
import com.example.policychecker2.ui.viewModels.ViewModel
import com.tom_roush.pdfbox.pdmodel.PDDocument.*
import com.tom_roush.pdfbox.text.PDFTextStripper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class ShowPdfFragment : Fragment(R.layout.fragment_show_pdf), ScanFiltersDialog.ScanFiltersDialogListener {

    private lateinit var binding: FragmentShowPdfBinding
    private val args: ShowPdfFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShowPdfBinding.bind(view)

        binding.tvPdfTitle.text = args.pdfFileTitle

        showPdfFromUri(args.uri)

        //ok
        binding.btnSetScanFilters.setOnClickListener {
            openDialog()
        }

    }


    private fun openDialog() {
        val scanFiltersDialog = ScanFiltersDialog(this)
        scanFiltersDialog.show(requireActivity().supportFragmentManager, "scan filters dialog")
    }

    //ok
    private fun showPdfFromUri(uri: Uri?) {
        binding.pdfView.fromUri(uri)
            .defaultPage(0)
            .spacing(10)
            .load()
    }


    override fun scanFileAndNavigate(spinnerFileType: String, spinnerLanguage: String) {

        val action = ShowPdfFragmentDirections.actionShowPdfFragmentToLoadingAndScanningFragment(args.uri, args.pdfFileTitle, spinnerFileType, spinnerLanguage)
        findNavController().navigate(action)
    }

}