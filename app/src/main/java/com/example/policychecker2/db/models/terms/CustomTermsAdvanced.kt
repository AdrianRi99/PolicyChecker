package com.example.policychecker2.db.models.terms

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "CustomTermsAdvanced")
class CustomTermsAdvanced (
    override var term: String
) : CustomTermsBaseClass(term) {

    override fun clone(): CustomTermsAdvanced {

        val stringCustomTermsAdvanced = Gson().toJson(this, CustomTermsAdvanced::class.java)
        return Gson().fromJson(stringCustomTermsAdvanced, CustomTermsAdvanced::class.java)
    }

}
