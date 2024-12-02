package com.example.policychecker2.db.models.terms

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose


abstract class TermsBaseClass (@Transient @ColumnInfo (name = "term") open var term :String,
                               @Transient @ColumnInfo(name = "termDescription") open val termDescription :String,
                               @Transient @ColumnInfo(name = "type") open val type : Int,
                               @Transient @ColumnInfo(name = "item") open val item : String,
                               @Transient @ColumnInfo(name = "indexRangeInText") open var indexRangeInText : String = "1..5",
                               @Transient @ColumnInfo(name = "visibility") var visibility : Int = 0) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    abstract fun clone() : TermsBaseClass
}

