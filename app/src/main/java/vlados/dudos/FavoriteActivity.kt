package vlados.dudos

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_favorite.*
import vlados.dudos.Adapters.GenreAdapter
import vlados.dudos.Adapters.SearchAdapter
import vlados.dudos.Case.request
import vlados.dudos.Models.Genre
import vlados.dudos.Models.Result
import vlados.dudos.app.App
import java.lang.Exception

class FavoriteActivity : AppCompatActivity(), SearchAdapter.OnClickListener,
    GenreAdapter.OnClickListener {

    override fun click(data: Result) {
        startActivity(Intent(this, InfoActivity::class.java))
        request = 1
    }

    override fun click(data: Genre) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        val disp = App.dm.api
            .getGenre()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ i ->
                rv_g.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                rv_g.adapter = GenreAdapter(i.genres, this, applicationContext)
            }, {
                Log.d("", "")
            })


        val sharedPreferences = getSharedPreferences("item_id", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("favorite_list", null)
        val turnsType = object : TypeToken<MutableList<Result>>() {}.type

        try {
            Case.favoritelist = gson.fromJson(json, turnsType)
            rv_favorite.layoutManager = LinearLayoutManager(this)
            rv_favorite.adapter = SearchAdapter(Case.favoritelist.reversed(), this)
        } catch (e: Exception) {
        }

        backarrow_f.setOnClickListener {
            super.onBackPressed()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
    }
}
