package com.example.policychecker2.db.daos.de

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.policychecker2.db.models.terms.de.KaufvertragDe
import com.example.policychecker2.db.models.terms.de.MietvertragDe

@Dao
interface KaufvertragDao {

    @Query("Select * from KaufvertragDe")
    fun getAllKaufvertragDeTerms(): List<KaufvertragDe>

    @Insert(onConflict = OnConflictStrategy.IGNORE)  //if you add exactly the same note
    suspend fun insertKaufvertragDe(term: KaufvertragDe) //suspend -> because of Kotlin coroutines

}