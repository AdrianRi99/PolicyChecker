import android.text.Spanned
import android.text.style.ClickableSpan
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder

val result = listOf(
    "First",
    "First,Second",
    "First,Second, Three, Four",
    "First,Second",
    "First",
    "First,Second",
    "First",

)

fun main() {

    val newResult = mutableListOf(listOf<String>())

    result.forEach {
        if(it.contains(",")) {
            newResult.add(it.split(","))
        } else {
            newResult.add(listOf(it))
        }
    }


    var line  = ""

    newResult.forEach {
        it.forEach {
            line += "$it, "
        }

        line = ""
    }


}



abstract class Car(var maxSpeed: Int) {

   open fun test (string : String) {
        println(string)
    }
}


class Ferrai : Car(1000) {

    fun test(string: Int) {

    }
}

