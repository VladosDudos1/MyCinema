package vlados.dudos


import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.Touch
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_now.*
import vlados.dudos.Adapters.GenreAdapter
import vlados.dudos.Case.openFragment
import vlados.dudos.Case.twString
import vlados.dudos.app.App
import vlados.dudos.fragments.NowFragment
import vlados.dudos.fragments.PopularFragment
import vlados.dudos.fragments.SoonFragment

class MainActivity : AppCompatActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openNowFragment()

        val disp = App.dm.api
            .getGenre()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({i ->
                rv_g.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                rv_g.adapter = GenreAdapter(i.genres)
            },{
                Log.d("", "")
            })

        text1.setOnClickListener {

            openNowFragment()
            text1.setTextColor(Color.parseColor("#000000"))
            text2.setTextColor(Color.parseColor("#40000000"))
            text3.setTextColor(Color.parseColor("#40000000"))
            text1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.bottom)
            text2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            text3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
        text2.setOnClickListener {
            openBoxFragment()
            text2.setTextColor(Color.parseColor("#000000"))
            text1.setTextColor(Color.parseColor("#40000000"))
            text3.setTextColor(Color.parseColor("#40000000"))
            text2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.bottom)
            text3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            text1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
        text3.setOnClickListener {
            openSoonFragment()
            text3.setTextColor(Color.parseColor("#000000"))
            text1.setTextColor(Color.parseColor("#40000000"))
            text2.setTextColor(Color.parseColor("#40000000"))
            text3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.bottom)
            text2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            text1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }

        search_btn.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    fun changeFragment(fmt: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_holder, fmt)
            .addToBackStack(null).commit()
    }

    fun openNowFragment() {
        changeFragment(NowFragment())
        openFragment = "now"

        text1.setTextColor(Color.parseColor("#000000"))
        text2.setTextColor(Color.parseColor("#40000000"))
        text3.setTextColor(Color.parseColor("#40000000"))
        text1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.bottom)
        text2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        text3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
    }

    fun openBoxFragment() {
        changeFragment(PopularFragment())
        openFragment = "box"
    }

    fun openSoonFragment() {
        changeFragment(SoonFragment())
        openFragment = "soon"
    }

    override fun onBackPressed() {

        if (openFragment != "now") {
            openNowFragment()
        } else finishAffinity()
    }
}
