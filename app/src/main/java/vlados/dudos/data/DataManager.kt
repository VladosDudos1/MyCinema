package vlados.dudos.data

import android.content.Context
import android.content.SharedPreferences

class DataManager {
    private val baseContext: Context
    private val shared: SharedPreferences

    val api = Api.createApi()

    constructor(baseContext: Context){
        this.baseContext = baseContext
        shared = baseContext.getSharedPreferences("Cinema", Context.MODE_PRIVATE)
    }
}