package vlados.dudos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.android.synthetic.main.activity_main.*
import vlados.dudos.Adapters.CastAdapter
import vlados.dudos.Adapters.GenreAdapter
import vlados.dudos.Case.favoritelist
import vlados.dudos.Case.id
import vlados.dudos.Case.item
import vlados.dudos.Case.request
import vlados.dudos.Models.Genre
import vlados.dudos.Models.GenreModel
import vlados.dudos.Models.Result
import vlados.dudos.app.App
import java.lang.Exception
import java.util.*
import kotlin.concurrent.timerTask

class InfoActivity : AppCompatActivity() {


    var jList = listOf<Genre>()

    var resultList = mutableListOf<Genre>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val sharedPreferences = getSharedPreferences("item_id", Context.MODE_PRIVATE)
        val gson2 = Gson()
        val json2 = sharedPreferences.getString("favorite_list", null)
        val turnsType = object : TypeToken<MutableList<Result>>() {}.type

        try {
            favoritelist = gson2.fromJson(json2, turnsType)
        } catch (e: Exception) {
        }

        try {
            Glide.with(img_info)
                .load("https://image.tmdb.org/t/p/w1280" + item!!.backdrop_path)
                .error(R.drawable.noimage)
                .into(img_info)
        } catch (e: Exception) {
        }

        favorite_card.setOnClickListener {
            card1.visibility = View.GONE
            card2.visibility = View.VISIBLE
            val gson = Gson()
            favoritelist.add(item!!)
            val json = gson.toJson(favoritelist)
            sharedPreferences.edit().putString("favorite_list", json).apply()
        }

        delete_card.setOnClickListener {
            card1.visibility = View.VISIBLE
            card2.visibility = View.GONE

            val gson = Gson()
            favoritelist.remove(item!!)
            val json = gson.toJson(favoritelist)
            sharedPreferences.edit().putString("favorite_list", json).apply()
        }

        try {
            name.text = item!!.title

            raiting_info.text = item!!.vote_average.toString()

            voices.text = item!!.vote_count.toString()

            plot.text = item!!.overview

            realise_date.text = item!!.release_date.removeRange(4..9)

            language_txt.text = item!!.original_language
        } catch (e: Exception) {
        }


        if (item in favoritelist) {
            card1.visibility = View.GONE
            card2.visibility = View.VISIBLE
        }
        if (item !in favoritelist) {
            card1.visibility = View.VISIBLE
            card2.visibility = View.GONE
        }

        if (item!!.adult == true) {
            adult.text = "18+"
        }
        if (item!!.adult == false) {
            adult.text = "Family Content"
        }

        val disp = App.dm.api
            .getActor(id.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ c ->
                rv_cast.layoutManager = GridLayoutManager(this, 4)
                rv_cast.adapter = CastAdapter(c.cast)
                pb_cast.visibility = View.GONE
                rv_cast.visibility = View.VISIBLE
            }, {
                Log.d("", "")
            })


        val dispose = App.dm.api
            .getGenre()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ i ->
                jList = i.genres
                for (item in item!!.genre_ids) {
                    for (ni in jList) {
                        if (item == ni.id) {
                            resultList.add(ni)
                        }
                    }
                }
                rv_con_g.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                rv_con_g.adapter = GenreAdapter(resultList)
            }, {
                Log.d("", "")
            })
    }

    fun back(view: View) {
        super.onBackPressed()
        if (request == 1) {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (request == 1) {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }
    }
}
