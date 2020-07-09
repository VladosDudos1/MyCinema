package vlados.dudos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_info.*
import kotlinx.android.synthetic.main.activity_main.*
import vlados.dudos.Adapters.CastAdapter
import vlados.dudos.Adapters.GenreAdapter
import vlados.dudos.Case.id
import vlados.dudos.Case.item
import vlados.dudos.Models.Genre
import vlados.dudos.Models.GenreModel
import vlados.dudos.app.App
import java.lang.Exception

class InfoActivity : AppCompatActivity() {

    var jList = listOf<Genre>()

    var resultList = mutableListOf<Genre>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        try {
            Glide.with(img_info)
                .load("https://image.tmdb.org/t/p/w1280" + item!!.backdrop_path)
                .error(R.drawable.noimage)
                .into(img_info)
        } catch (e: Exception) {
        }


        name.text = item!!.original_title

        raiting_info.text = item!!.vote_average.toString()

        voices.text = item!!.popularity.toString()

        plot.text = item!!.overview

        realise_date.text = item!!.release_date.removeRange(4..9)

        language_txt.text = item!!.original_language

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
    }
}
