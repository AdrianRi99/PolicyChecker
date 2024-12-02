package com.example.policychecker2.repositories

import androidx.lifecycle.LiveData
import com.example.policychecker2.db.daos.de.MietvertagDao
import com.example.policychecker2.db.daos.FilesDao
import com.example.policychecker2.db.daos.de.KaufvertragAdvancedDao
import com.example.policychecker2.db.daos.de.KaufvertragDao
import com.example.policychecker2.db.daos.de.MietvertagAdvancedDao
import com.example.policychecker2.db.daos.eng.MietvertagEngAdvancedDao
import com.example.policychecker2.db.daos.eng.MietvertagEngDao
import com.example.policychecker2.db.models.terms.de.MietvertragDe
import com.example.policychecker2.db.models.Files
import com.example.policychecker2.db.models.terms.de.KaufvertragDe
import com.example.policychecker2.db.models.terms.de.KaufvertragDeAdvanced
import com.example.policychecker2.db.models.terms.de.MietvertragDeAdvanced
import com.example.policychecker2.db.models.terms.eng.MietvertragEng
import com.example.policychecker2.db.models.terms.eng.MietvertragEngAdvanced
import javax.inject.Inject

class Repository @Inject constructor(
    private val mietvertagDao: MietvertagDao,
    private val mietvertragAdvancedDao: MietvertagAdvancedDao,
    private val kaufvertragDeDao: KaufvertragDao,
    private val kaufvertragDeAdvancedDao: KaufvertragAdvancedDao,
    private val mietvertagEngDao: MietvertagEngDao,
    private val mietvertagEngAdvancedDao: MietvertagEngAdvancedDao,
    private val filesDao: FilesDao
    ){

    //De
    val allMietvertragDeTerms : List<MietvertragDe> = mietvertagDao.getAllMietvertragTerms()
    val allMietvertragDeAdvancedTerms: List<MietvertragDeAdvanced> = mietvertragAdvancedDao.getAllMietvertragAdvancedTerms()

    val allKaufvertragDeTerms : List<KaufvertragDe> = kaufvertragDeDao.getAllKaufvertragDeTerms()
    val allKaufvertragDeAdvancedTerms: List<KaufvertragDeAdvanced> = kaufvertragDeAdvancedDao.getAllKaufvertragDeAdvancedTerms()

    //Eng
    val allMietvertragEngTerms : List<MietvertragEng> = mietvertagEngDao.getAllMietvertragEngTerms()
    val allMietvertragEngAdvancedTerms: List<MietvertragEngAdvanced> = mietvertagEngAdvancedDao.getAllMietvertragEngAdvancedTerms()


    //De
    suspend fun insertMietvertragDe(terms: MietvertragDe) {
        mietvertagDao.insertMietvertragDe(terms)
    }

    suspend fun insertMietvertragDeAdvanced(terms: MietvertragDeAdvanced) {
        mietvertragAdvancedDao.insertMietvertragDeAdvanced(terms)
    }

    suspend fun insertKaufvertragDe(terms: KaufvertragDe) {
        kaufvertragDeDao.insertKaufvertragDe(terms)
    }

    suspend fun insertKaufvertragDeAdvanced(terms: KaufvertragDeAdvanced) {
        kaufvertragDeAdvancedDao.insertKaufvertragDeAdvanced(terms)
    }


    //Eng
    suspend fun insertMietvertragEng(terms: MietvertragEng) {
        mietvertagEngDao.insertMietvertragEng(terms)
    }

    suspend fun insertMietvertragEngAdvanced(terms: MietvertragEngAdvanced) {
        mietvertagEngAdvancedDao.insertMietvertragEngAdvanced(terms)
    }

    //work with files
    val allFiles : LiveData<List<Files>> = filesDao.getAllFiles()

    suspend fun insert(file: Files) {
        filesDao.insert(file)
    }

    suspend fun delete(file: Files) {
        filesDao.delete(file)
    }

    suspend fun updateFileTitle(fileId: Int, newTitle: String) {
        filesDao.updateFileTitle(fileId, newTitle)
    }

}