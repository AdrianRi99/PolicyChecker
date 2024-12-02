package com.example.policychecker2.db.daos.eng

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.policychecker2.db.models.terms.de.MietvertragDeAdvanced
import com.example.policychecker2.db.models.terms.eng.MietvertragEngAdvanced

@Dao
interface MietvertagEngAdvancedDao {

    @Query("Select * from MietvertragEngAdvanced")
    fun getAllMietvertragEngAdvancedTerms(): List<MietvertragEngAdvanced>

    @Insert(onConflict = OnConflictStrategy.IGNORE)  //if you add exactly the same note
    suspend fun insertMietvertragEngAdvanced(term: MietvertragEngAdvanced) //suspend -> because of Kotlin coroutines

}