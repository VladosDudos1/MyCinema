package vlados.dudos

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import vlados.dudos.Adapters.GenreAdapter
import vlados.dudos.Case.openFragment
import vlados.dudos.Case.request
import vlados.dudos.Models.Genre
import vlados.dudos.app.App
import vlados.dudos.fragments.NowFragment
import vlados.dudos.fragments.PopularFragment
import vlados.dudos.fragments.SoonFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openNowFragment()

        toFavorite.setOnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
        }

        text1.setOnClickListener {

            sc_view.smoothScrollTo(0, 0)
            openNowFragment()
            text1.setTextColor(Color.parseColor("#000000"))
            text2.setTextColor(Color.parseColor("#40000000"))
            text3.setTextColor(Color.parseColor("#40000000"))
            text1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.bottom)
            text2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            text3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
        text2.setOnClickListener {
            sc_view.smoothScrollTo(350, 0)
            openBoxFragment()
            text2.setTextColor(Color.parseColor("#000000"))
            text1.setTextColor(Color.parseColor("#40000000"))
            text3.setTextColor(Color.parseColor("#40000000"))
            text2.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.bottom)
            text3.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
            text1.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
        text3.setOnClickListener {
            sc_view.smoothScrollTo(1000, 0)
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

        request = 0
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
        request = 0
    }

    fun openSoonFragment() {
        changeFragment(SoonFragment())
        openFragment = "soon"
        request = 0
    }

    override fun onBackPressed() {
        if (openFragment != "now") {
            openNowFragment()
        } else finishAffinity()
    }
}
