package com.example.policychecker2.db.models.terms

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.policychecker2.db.models.terms.TermsBaseClass
import com.example.policychecker2.db.models.terms.de.KaufvertragDe
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "CustomTerms")
class CustomTerms (
     override var term: String
) : CustomTermsBaseClass(term) {

     override fun clone(): CustomTerms {

          val stringCustomTerms = Gson().toJson(this, CustomTerms::class.java)
          return Gson().fromJson(stringCustomTerms, CustomTerms::class.java)
     }
}

