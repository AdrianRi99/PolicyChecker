package com.example.policychecker2.db.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.policychecker2.db.models.Files

@Dao
interface FilesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)  //if you add exactly the same note
    suspend fun insert(file: Files) //suspend -> because of Kotlin coroutines

    @Delete
    suspend fun delete(file: Files)

    @Query("Select * from FilesTable")
    fun getAllFiles(): LiveData<List<Files>>

    @Query("UPDATE FilesTable SET fileTitle = :newTitle WHERE id = :fileId")
    suspend fun updateFileTitle(fileId: Int, newTitle: String)

}