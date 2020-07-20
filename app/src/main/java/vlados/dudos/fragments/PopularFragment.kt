package vlados.dudos.fragments

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import kotlinx.android.synthetic.main.popular_fragment.*
import kotlinx.android.synthetic.main.popular_fragment.view.*
import vlados.dudos.Adapters.RvAdapter
import vlados.dudos.Adapters.RvAdapterPopular
import vlados.dudos.InfoActivity
import vlados.dudos.Models.Result
import vlados.dudos.R
import vlados.dudos.app.App



class PopularFragment : Fragment(), RvAdapterPopular.OnClickListener {


    var page = 1
    var newPop = mutableListOf<Result>()


    override fun click(data: Result){
        startActivity(Intent(activity, InfoActivity::class.java))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val asd = inflater.inflate(R.layout.popular_fragment, container, false)
        val layout = asd.layout
        val recycler = asd.rv_popular
        val progressB = asd.pb_popular


        val disp = App.dm.api
            .getPopular(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ p ->
                for (i in p.results){
                    newPop.add(i)
                }
                recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                recycler.adapter = RvAdapterPopular(newPop, this)
                layout.visibility = View.VISIBLE
                recycler.visibility = View.VISIBLE
                progressB.visibility = View.GONE
            }, {
                Toast.makeText(activity, "You have no internet!", Toast.LENGTH_LONG).show()
            })

        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener(), RvAdapter.OnClickListener {
            override fun click(data: Result) {
                startActivity(Intent(activity, InfoActivity::class.java))
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int){
                super.onScrollStateChanged(recyclerView, newState)
                val manager: LinearLayoutManager = recycler.getLayoutManager() as LinearLayoutManager

                if (newState == 0 && manager.findLastVisibleItemPosition() == newPop.size - 1) {
                    pb_load.visibility = View.VISIBLE
                    page+=1
                    val dispose = App.dm.api
                        .getPopular(page)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({g ->

                            for (i in g.results){
                                newPop.add(i)
                            }


                            recycler.adapter!!.notifyItemInserted(newPop.size - g.results.size)

                            pb_load.visibility = View.GONE
                            recycler.smoothScrollToPosition(newPop.size - g.results.size)
                        }, {
                            Toast.makeText(activity, "You have no internet!", Toast.LENGTH_LONG).show()
                        })
                }
                if (manager.findFirstVisibleItemPosition() > 0){
                    start_rv_arrow.visibility = View.VISIBLE
                } else start_rv_arrow.visibility = View.GONE
            }
        })

        return asd
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        start_rv_arrow.setOnClickListener {
            rv_popular.smoothScrollToPosition(0)
            pb_load.visibility = View.GONE
        }
    }
}

