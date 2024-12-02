//package com.example.policychecker2.ui.fragments
//
//import android.os.Bundle
//import androidx.fragment.app.Fragment
//import android.view.View
//import androidx.core.content.ContextCompat
//import androidx.navigation.fragment.navArgs
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.policychecker2.R
//import com.example.policychecker2.adapters.AllTermsAdapter
//import com.example.policychecker2.databinding.FragmentShowAllTermsBinding
//
//
//class ShowAllTermsFragment : Fragment(R.layout.fragment_show_all_terms) {
//
//    private lateinit var binding: FragmentShowAllTermsBinding
//
////    private val args: ShowAllTermsFragmentArgs by navArgs()
//
//    private lateinit var allTermsAdapter: AllTermsAdapter
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        binding = FragmentShowAllTermsBinding.bind(view)
//
//
//        val type = args.allTerms[0].type
//        when(type) {
//            1 -> {
//                binding.tvHeaderAllTerms.text = "Basic Terms"
//                binding.tvDescAllTerms.text = "Terms related to the basic data of the contract"
//                binding.dividerAllTerms.dividerColor = ContextCompat.getColor(requireActivity(), R.color.colorBasicTerm)
//            }
//            2 -> {
//                binding.tvHeaderAllTerms.text = "Dangerous Terms"
//                binding.tvDescAllTerms.text = "Terms that can work to your disadvantage"
//                binding.dividerAllTerms.dividerColor = ContextCompat.getColor(requireActivity(), R.color.colorDangerousTerm)
//            }
//            3 -> {
//                binding.tvHeaderAllTerms.text = "Invalid Terms"
//                binding.tvDescAllTerms.text = "Terms that do not comply with the law"
//                binding.dividerAllTerms.dividerColor = ContextCompat.getColor(requireActivity(), R.color.colorInvalidTerms)
//            }
//        }
//
//
//
//        setupRecyclerView()
//
//        var allTerms = args.allTerms
//        allTerms = allTerms.sortedBy {
//            it.item
//        }.toTypedArray()
//
//        allTermsAdapter.updateListOfTerms(allTerms.toMutableList())
//    }
//
//    //Ok
//    private fun setupRecyclerView() = binding.rvAllTermsFragment.apply {
//        allTermsAdapter = AllTermsAdapter()
//        adapter = allTermsAdapter
//        layoutManager = LinearLayoutManager(requireContext())
//    }
//}