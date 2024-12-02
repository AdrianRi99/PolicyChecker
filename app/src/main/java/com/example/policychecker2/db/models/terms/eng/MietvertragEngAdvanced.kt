package com.example.policychecker2.db.models.terms.eng

import androidx.room.Entity
import com.example.policychecker2.db.models.terms.TermsBaseClass
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "MietvertragEngAdvanced")
class MietvertragEngAdvanced (
    override var term: String,
    override val termDescription: String,
    override val type: Int,
    override val item: String
) : TermsBaseClass(term, termDescription, type, item){  //Int -> 0 represents false - 1 true

    override fun clone(): MietvertragEngAdvanced
    {
        val stringMietvertragAdvanced = Gson().toJson(this, MietvertragEngAdvanced::class.java)
        return Gson().fromJson(stringMietvertragAdvanced, MietvertragEngAdvanced::class.java)
    }
}


//@Parcelize
//@Entity(tableName = "DangerousTermsTable")
//class MietvertragDe (@ColumnInfo(name = "dangerousTerm") val dangerousTerm :String,
//                      @ColumnInfo(name = "termEasyTranslation") val termEasyTranslation :String,
//                      @ColumnInfo(name = "visibility") var visibility : Int = 0) : Parcelable{  //Int -> 0 represents false - 1 true
//    @PrimaryKey(autoGenerate = true)
//    var id = 0
//}