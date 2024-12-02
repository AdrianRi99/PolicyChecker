package com.example.policychecker2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
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


@Database(entities = [MietvertragDe::class, MietvertragDeAdvanced::class, KaufvertragDe::class, KaufvertragDeAdvanced::class, MietvertragEng::class, MietvertragEngAdvanced::class, Files::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class DatabaseApp : RoomDatabase() {

    //De
    abstract fun getMietvertragDao(): MietvertagDao
    abstract fun getMietvertragAdvancedDao(): MietvertagAdvancedDao
    abstract fun getKaufvertragDeDao(): KaufvertragDao
    abstract fun getKaufvertragDeAdvancedDao(): KaufvertragAdvancedDao

    //Eng
    abstract fun getMietvertragEngDao(): MietvertagEngDao
    abstract fun getMietvertragEngAdvancedDao(): MietvertagEngAdvancedDao
    abstract fun getFilesDao(): FilesDao

}