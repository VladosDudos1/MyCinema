package vlados.dudos

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Scheduler
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
import vlados.dudos.Models.RateBodyModel
import vlados.dudos.Models.Result
import vlados.dudos.app.App
import java.lang.Exception
import java.util.*
import kotlin.concurrent.timerTask

class InfoActivity : AppCompatActivity(), GenreAdapter.OnClickListener {

    override fun click(data: Genre) {

    }

    var jList = listOf<Genre>()

    var resultList = mutableListOf<Genre>()

    var video_key = ""

    var bodyElement: RateBodyModel = RateBodyModel(0.0)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        rate_layout.setOnClickListener {

            MaterialDialog(this)
                .noAutoDismiss()
                .title(text = "Выберите оценку")
                .listItemsSingleChoice(
                    items = listOf(
                        "1",
                        "2",
                        "3",
                        "4",
                        "5",
                        "6",
                        "7",
                        "8",
                        "9",
                        "10"
                    ), selection = { dialog, i, text ->
                        bodyElement = when (i) {
                            0 -> RateBodyModel(1.0)
                            1 -> RateBodyModel(2.0)
                            2 -> RateBodyModel(3.0)
                            3 -> RateBodyModel(4.0)
                            4 -> RateBodyModel(5.0)
                            5 -> RateBodyModel(6.0)
                            6 -> RateBodyModel(7.0)
                            7 -> RateBodyModel(8.0)
                            8 -> RateBodyModel(9.0)
                            else -> RateBodyModel(10.0)
                        }
                        dialog.cancel()
                        postReq()
                    }).show { }
        }

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
                rv_con_g.adapter = GenreAdapter(resultList, this, applicationContext)
            }, {
                Log.d("", "")
            })

        val dispXD = App.dm.api
            .findTrailer(item!!.id.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ i ->
                video_key = i.results[0].key
            }, {
                youtube_trailer.visibility = View.GONE
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

    fun openTrailer(view: View) {
        try {
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse("https://youtube.com/watch?v=" + video_key)
            startActivity(openUrl)
        } catch (e: Exception) {
        }
    }

    fun postReq() {

        var guest = ""

        val getGuest = App.dm.api
            .guestSession()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ i ->
                guest = i.guest_session_id
            }, {
                Log.d("", "")
            }, {
                val disp = App.dm.api
                    .postValue(item!!.id.toString(), guest, bodyElement)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ p ->
                        Toast.makeText(
                            this,
                            "Спасибо за ваш отзыв!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }, {
                        Log.d("", "")
                    })
            })
    }
}
