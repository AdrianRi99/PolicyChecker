package com.example.policychecker2.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.policychecker2.R
import com.example.policychecker2.adapters.FileItemClickInterface
import com.example.policychecker2.adapters.FileOnLongClickInterface
import com.example.policychecker2.adapters.HomeAdapter
import com.example.policychecker2.databinding.FragmentHomeBinding
import com.example.policychecker2.db.models.terms.de.MietvertragDe
import com.example.policychecker2.db.models.Files
import com.example.policychecker2.db.models.terms.de.MietvertragDeAdvanced
import com.example.policychecker2.db.models.terms.eng.MietvertragEng
import com.example.policychecker2.db.models.terms.eng.MietvertragEngAdvanced
import com.example.policychecker2.ui.dialogs.*
import com.example.policychecker2.ui.viewModels.ViewModel
import com.example.policychecker2.utility.Download
import com.example.policychecker2.utility.Functions
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint

//for all Android Components like Activity, Fragments, Services AndroidEntryPoint is needed
@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home), FileItemClickInterface, FileOnLongClickInterface, LanguagePackageDialog.LanguagePackageDialogListener, RenameFileDialog.RenameDialogListener, DeleteDialog.DeleteDialogListener{

    private lateinit var binding: FragmentHomeBinding
    private val viewModal: ViewModel by viewModels()

    private val args: HomeFragmentArgs by navArgs()

    private lateinit var homeAdapter: HomeAdapter

    private val pdfSelectionCode = 100

    lateinit var downloadObject: Download

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        downloadObject = Download(viewModal)
        //ok
        setupRecyclerView()

        //Gradient Animated Background
//        val animationDrawable: AnimationDrawable = binding.homeFragment.background as AnimationDrawable
//        animationDrawable.setEnterFadeDuration(2500)
//        animationDrawable.setExitFadeDuration(5000)
//        animationDrawable.start()

        //when coming from saving the resultFragment
        if(args.scrollToBottom) {
            binding.rvHomeFragment.postDelayed({
                binding.rvHomeFragment.scrollToPosition(homeAdapter.differ.currentList.size - 1)
            },1000)
        }



        //ok
        binding.btnOpenPdf.setOnClickListener {
            Log.d("What", "Niceeeee")
           selectPdfFromStorage()
        }

        binding.icLanguageDownloaded.setOnClickListener {
            showHomeMenu(it)
        }

        //Ok
        viewModal.allFiles.observe(viewLifecycleOwner) { files ->

            if(files.toString() != "[]"){
                binding.tvNoFile.text = ""
            }else{
                binding.tvNoFile.text = "NO FILE YET - ADD ONE BY CLICKING ON 'CHOOSE FILE'"
            }

            files?.let {
                homeAdapter.updateListOfFiles(it)
            }
        }


    }



    private fun showHomeMenu(view: View) {
        val homeMenu = PopupMenu(requireActivity(), view)
        homeMenu.inflate(R.menu.home_menu)

        homeMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_languages -> {
                    openLanguagePackageDialog()
                    true
                }
//                R.id.menu_delete -> {
//                    // Hier können Sie die Aktion für die Option "Delete" ausführen
//                    Toast.makeText(requireActivity(), "Delete", Toast.LENGTH_SHORT).show()
//
//                    true
//                }
                else -> false
            }
        }

        homeMenu.show()
    }

    override fun onLongClick(view: View, file: Files) {


        showFileMenu(view, file)

    }

    private fun showFileMenu(view: View, file: Files) {
        val fileMenu = PopupMenu(requireActivity(), view)
        fileMenu.inflate(R.menu.file_menu)

        fileMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_rename -> {

                    renameFile(file)
                    true
                }
                R.id.menu_delete -> {

                    deleteFileDialog(file)

                    true
                }
                else -> false
            }
        }

        fileMenu.show()
    }

    private fun deleteFileDialog(file: Files){

        val deleteDialog = DeleteDialog(this, file)
        deleteDialog.show(requireActivity().supportFragmentManager, "delete dialog")
//        MaterialAlertDialogBuilder(requireContext())
//            .setTitle("Delete File")
//            .setMessage("Do you want to delete file '${file.fileTitle}' ?")
//            .setNegativeButton("CANCEL") { dialog, which ->
//
//            }
//            .setPositiveButton("OK") { dialog, which ->
//                viewModal.deleteFile(file)
//                Toast.makeText(requireContext(), "${file.fileTitle} deleted", Toast.LENGTH_LONG).show()
//            }
//            .show()
    }

    private fun renameFile(file: Files){
        val renameFileDialog = RenameFileDialog(this, file)
        renameFileDialog.show(requireActivity().supportFragmentManager, "save dialog")
    }

    private fun openLanguagePackageDialog() {
        val languagePackageDialog = LanguagePackageDialog(this)
        languagePackageDialog.show(requireActivity().supportFragmentManager, "Language Package Dialog")
    }


    //Ok
    private fun setupRecyclerView() = binding.rvHomeFragment.apply {
        homeAdapter = HomeAdapter(this@HomeFragment, this@HomeFragment)
        adapter = homeAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    private val pdfSelectionLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            handleSelectedPdf(data)
        } else {
            // Die Auswahl wurde abgebrochen oder ein anderer Fehler ist aufgetreten
        }
    }

    // Funktion, um PDF auszuwählen
    private fun selectPdfFromStorage() {
        Toast.makeText(requireContext(), "Select PDF File", Toast.LENGTH_SHORT).show()
        val browserStorage = Intent(Intent.ACTION_GET_CONTENT)
        browserStorage.type = "application/pdf"
        browserStorage.addCategory(Intent.CATEGORY_OPENABLE)
        pdfSelectionLauncher.launch(Intent.createChooser(browserStorage, "Select Pdf"))
    }

    // Funktion zum Verarbeiten des ausgewählten PDFs
    private fun handleSelectedPdf(data: Intent?) {
        if (data != null) {
            val selectedPdf = data.data  //ist vom Typ URI

            val pdfFileTitle = Functions.getPdfFileName(requireActivity(), selectedPdf)
            val action = HomeFragmentDirections.actionHomeFragmentToShowPdfFragment(selectedPdf, pdfFileTitle)

            findNavController().navigate(action)
        } else {
            // Es wurde kein PDF ausgewählt oder ein anderer Fehler ist aufgetreten
        }
    }

    //Ok
    override fun onNoteClick(file: Files) {

        val resultsFound = file.resultsFound

        val content = file.content

        val action = HomeFragmentDirections.actionHomeFragmentToResultsFragment(content, resultsFound.toTypedArray(), file.fileTitle, "saved")
        findNavController().navigate(action)
    }


    override fun downloadLanguagePackage(spinnerLanguage: String) {

        downloadObject.downloadLanguagePackage(spinnerLanguage)

    }

    override fun renameFileTitle(file: Files, newTitle: String) {
        viewModal.updateFileTitle(file.id, newTitle)
    }

    override fun deleteFile(file: Files) {
        viewModal.deleteFile(file)
    }

}