package com.example.policychecker2.db.daos.de

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.policychecker2.db.models.terms.de.MietvertragDe

@Dao
interface MietvertagDao {

    @Query("Select * from MietvertragDe")
    fun getAllMietvertragTerms(): List<MietvertragDe>

    @Insert(onConflict = OnConflictStrategy.IGNORE)  //if you add exactly the same note
    suspend fun insertMietvertragDe(term: MietvertragDe) //suspend -> because of Kotlin coroutines

}