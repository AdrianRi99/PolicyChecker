package com.example.policychecker2.db.models.terms

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose


abstract class CustomTermsBaseClass (@Transient @ColumnInfo (name = "term") open var term :String,
                                     @Transient @ColumnInfo(name = "type") open val type : Int = 4,
                                     @Transient @ColumnInfo(name = "indexRangeInText") open var indexRangeInText : String = "1..5",
                                     ) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    abstract fun clone() : CustomTermsBaseClass
}

