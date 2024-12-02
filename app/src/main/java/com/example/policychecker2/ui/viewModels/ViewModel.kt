package com.example.policychecker2.ui.viewModels


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.policychecker2.db.models.terms.de.MietvertragDe
import com.example.policychecker2.db.models.Files
import com.example.policychecker2.db.models.terms.de.KaufvertragDe
import com.example.policychecker2.db.models.terms.de.KaufvertragDeAdvanced
import com.example.policychecker2.db.models.terms.de.MietvertragDeAdvanced
import com.example.policychecker2.db.models.terms.eng.MietvertragEng
import com.example.policychecker2.db.models.terms.eng.MietvertragEngAdvanced
import com.example.policychecker2.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val repository: Repository  //even tough we do not have mainRepository provides function in the AppModule, injecting works because MainRepository
): ViewModel() {                //only needs RunDao as a Parameter, and Dagger knows how to create a RunDao - MAGIC


    //De
    private val allMietvertragDeTerms: List<MietvertragDe> = repository.allMietvertragDeTerms
    private val allMietvertragDeAdvancedTerms: List<MietvertragDeAdvanced> = repository.allMietvertragDeAdvancedTerms

    private val allKaufvertragDeTerms: List<KaufvertragDe> = repository.allKaufvertragDeTerms
    private val allKaufvertragDeAdvancedTerms: List<KaufvertragDeAdvanced> = repository.allKaufvertragDeAdvancedTerms

    //Eng
    private val allMietvertragEngTerms: List<MietvertragEng> = repository.allMietvertragEngTerms
    private val allMietvertragEngAdvancedTerms: List<MietvertragEngAdvanced> = repository.allMietvertragEngAdvancedTerms

    val allFiles : LiveData<List<Files>> = repository.allFiles


    //De
    fun getMietvertragDeTerms() = allMietvertragDeTerms
    fun getMietvertragDeAdvancedTerms() = allMietvertragDeAdvancedTerms
    fun getKaufvertragDeTerms() = allKaufvertragDeTerms
    fun getKaufvertragDeAdvancedTerms() = allKaufvertragDeAdvancedTerms

    //Eng
    fun getMietvertragEngTerms() = allMietvertragEngTerms
    fun getMietvertragEngAdvancedTerms() = allMietvertragEngAdvancedTerms




    //De
    fun addMietvertragDeTerms(term: MietvertragDe) = viewModelScope.launch(Dispatchers.IO) {  //Dispatchers.IO -> to run code in Background Threat
        Log.d("Yi", "you")
        repository.insertMietvertragDe(term)


    }

    fun addMietvertragDeAdvancedTerms(term: MietvertragDeAdvanced) = viewModelScope.launch(Dispatchers.IO) {  //Dispatchers.IO -> to run code in Background Threat
        repository.insertMietvertragDeAdvanced(term)
    }

    fun addKaufvertragDeTerms(term: KaufvertragDe) = viewModelScope.launch(Dispatchers.IO) {  //Dispatchers.IO -> to run code in Background Threat
        Log.d("Yi", "you")
        repository.insertKaufvertragDe(term)
    }

    fun addKaufvertragDeAdvancedTerms(term: KaufvertragDeAdvanced) = viewModelScope.launch(Dispatchers.IO) {  //Dispatchers.IO -> to run code in Background Threat
        repository.insertKaufvertragDeAdvanced(term)
    }

    //Eng
    fun addMietvertragEngTerms(term: MietvertragEng) = viewModelScope.launch(Dispatchers.IO) {  //Dispatchers.IO -> to run code in Background Threat
        repository.insertMietvertragEng(term)
    }

    fun addMietvertragEngAdvancedTerms(term: MietvertragEngAdvanced) = viewModelScope.launch(Dispatchers.IO) {  //Dispatchers.IO -> to run code in Background Threat
        repository.insertMietvertragEngAdvanced(term)
    }

    //Additional
    fun addFile(file: Files) = viewModelScope.launch(Dispatchers.IO) {  //Dispatchers.IO -> to run code in Background Threat
        repository.insert(file)
    }

    fun deleteFile(file: Files) = viewModelScope.launch(Dispatchers.IO) {  //Dispatchers.IO -> to run code in Background Threat
        repository.delete(file)
    }

    fun updateFileTitle(fileId: Int, newTitle: String) = viewModelScope.launch(Dispatchers.IO) {  //Dispatchers.IO -> to run code in Background Threat
        repository.updateFileTitle(fileId, newTitle)
    }

}