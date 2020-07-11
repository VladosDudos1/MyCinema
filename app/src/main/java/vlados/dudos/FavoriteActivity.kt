package vlados.dudos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_favorite.*
import vlados.dudos.Adapters.SearchAdapter
import vlados.dudos.Case.request
import vlados.dudos.Models.Result
import java.lang.Exception
import java.lang.reflect.Type

class FavoriteActivity : AppCompatActivity(), SearchAdapter.OnClickListener {

    override fun click(data: Result) {
        startActivity(Intent(this, InfoActivity::class.java))
        request = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val sharedPreferences = getSharedPreferences("item_id", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("favorite_list", null)
        val turnsType = object : TypeToken<MutableList<Result>>() {}.type

        try {
            Case.favoritelist = gson.fromJson(json, turnsType)
            rv_favorite.layoutManager = LinearLayoutManager(this)
            rv_favorite.adapter = SearchAdapter(Case.favoritelist.reversed(), this)
        }catch (e:Exception){}

        backarrow_f.setOnClickListener {
            super.onBackPressed()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
