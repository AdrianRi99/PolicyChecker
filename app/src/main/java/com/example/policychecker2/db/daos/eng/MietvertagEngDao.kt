package com.example.policychecker2.db.daos.eng

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.policychecker2.db.models.terms.de.MietvertragDe
import com.example.policychecker2.db.models.terms.eng.MietvertragEng

@Dao
interface MietvertagEngDao {

    @Query("Select * from MietvertragEng")
    fun getAllMietvertragEngTerms(): List<MietvertragEng>

    @Insert(onConflict = OnConflictStrategy.IGNORE)  //if you add exactly the same note
    suspend fun insertMietvertragEng(term: MietvertragEng) //suspend -> because of Kotlin coroutines

}