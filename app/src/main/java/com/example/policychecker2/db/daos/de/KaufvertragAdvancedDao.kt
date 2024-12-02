package com.example.policychecker2.db.daos.de

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.policychecker2.db.models.terms.de.KaufvertragDeAdvanced
import com.example.policychecker2.db.models.terms.de.MietvertragDeAdvanced

@Dao
interface KaufvertragAdvancedDao {

    @Query("Select * from KaufvertragDeAdvanced")
    fun getAllKaufvertragDeAdvancedTerms(): List<KaufvertragDeAdvanced>

    @Insert(onConflict = OnConflictStrategy.IGNORE)  //if you add exactly the same note
    suspend fun insertKaufvertragDeAdvanced(term: KaufvertragDeAdvanced) //suspend -> because of Kotlin coroutines

}