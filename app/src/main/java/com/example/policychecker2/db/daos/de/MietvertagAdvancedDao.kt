package com.example.policychecker2.db.daos.de

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.policychecker2.db.models.terms.de.MietvertragDeAdvanced

@Dao
interface MietvertagAdvancedDao {

    @Query("Select * from MietvertragDeAdvanced")
    fun getAllMietvertragAdvancedTerms(): List<MietvertragDeAdvanced>

    @Insert(onConflict = OnConflictStrategy.IGNORE)  //if you add exactly the same note
    suspend fun insertMietvertragDeAdvanced(term: MietvertragDeAdvanced) //suspend -> because of Kotlin coroutines

}