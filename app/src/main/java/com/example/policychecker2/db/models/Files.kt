package com.example.policychecker2.db.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.policychecker2.db.models.terms.de.MietvertragDe
import java.io.Serializable




@Entity(tableName = "FilesTable")
class Files (@ColumnInfo(name = "fileTitle") val fileTitle :String,
              @ColumnInfo(name = "timeStamp") val timeStamp :String,
             @ColumnInfo(name = "content") val content :String,
             @ColumnInfo(name = "resultsFound") val resultsFound : List<MietvertragDe>
             ):
    Serializable {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
//
//
//@ColumnInfo(name = "resultsFound") val resultsFound :Map<String, String>
//
//
