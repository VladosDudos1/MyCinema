package vlados.dudos.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.coming_soon_fragment.*
import kotlinx.android.synthetic.main.coming_soon_fragment.view.*
import vlados.dudos.Adapters.RvAdapter
import vlados.dudos.Adapters.RvSoonAdapter
import vlados.dudos.InfoActivity
import vlados.dudos.Models.Result
import vlados.dudos.R
import vlados.dudos.app.App

class SoonFragment : Fragment(), RvSoonAdapter.OnClickListener {


    var page = 1
    var newSoon = mutableListOf<Result>()


    override fun click(data: Result) {
        startActivity(Intent(activity, InfoActivity::class.java))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val asd = inflater.inflate(R.layout.coming_soon_fragment, container, false)
        val recycler = asd.rv_soon
        val layout = asd.layoutF
        val progressB = asd.pb_soon
        val start_rv = asd.start_rv_arrow_s

        start_rv.setOnClickListener(this::start_rvScroll)


        val disp = App.dm.api
            .getSoon(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ s ->

                for (i in s.results) {
                    newSoon.add(i)
                }
                recycler.layoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                recycler.adapter = RvSoonAdapter(newSoon, this)
                layout.visibility = View.VISIBLE
                recycler.visibility = View.VISIBLE
                progressB.visibility = View.GONE
            }, {
                Toast.makeText(activity, "You have no internet!", Toast.LENGTH_LONG).show()
            })

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener(),
            RvAdapter.OnClickListener {
            override fun click(data: Result) {
                startActivity(Intent(activity, InfoActivity::class.java))
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val manager: LinearLayoutManager =
                    recycler.layoutManager as LinearLayoutManager

                if (newState == 0 && manager.findLastVisibleItemPosition() == newSoon.size - 1) {
                    page += 1
                    val dispose = App.dm.api
                        .getSoon(page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ g ->
                            pb_load_s.visibility = View.VISIBLE

                            for (i in g.results) {
                                newSoon.add(i)
                            }


                            recycler.adapter!!.notifyItemInserted(newSoon.size - g.results.size)
                            pb_load_s.visibility = View.GONE
                        }, {
                            Toast.makeText(activity, "You have no internet!", Toast.LENGTH_LONG)
                                .show()
                        })
                }
                if (manager.findFirstVisibleItemPosition() != 0) {
                    start_rv.visibility = View.VISIBLE
                } else start_rv.visibility = View.GONE
            }
        })
        return asd
    }

    fun start_rvScroll(view: View) {
        rv_soon.smoothScrollToPosition(0)
        pb_load_s.visibility = View.GONE
    }
}