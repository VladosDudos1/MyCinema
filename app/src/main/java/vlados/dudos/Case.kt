package vlados.dudos

import android.content.Context
import com.google.gson.Gson
import vlados.dudos.Models.Result

object Case {

    var openFragment = ""
    var item: Result? = null
    var id: Int? = null
    var twString = ""


    var request = 0
    var favoritelist = mutableListOf<Result>()

}