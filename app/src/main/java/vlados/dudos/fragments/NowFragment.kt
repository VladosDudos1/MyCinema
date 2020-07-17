package vlados.dudos.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_now.view.*
import vlados.dudos.R
import vlados.dudos.Adapters.RvAdapter
import vlados.dudos.Case.request
import vlados.dudos.InfoActivity
import vlados.dudos.Models.Result
import vlados.dudos.app.App





class NowFragment : Fragment(), RvAdapter.OnClickListener {

    override fun click(data: Result){
        startActivity(Intent(activity, InfoActivity::class.java))
        request = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val asd =  inflater.inflate(R.layout.fragment_now, container, false)
        val recycler = asd.rv
        val progressB = asd.pb


        val disp = App.dm.api
            .getNowPlaing()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({i ->
                recycler.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                recycler.adapter = RvAdapter(i.results, this)
                recycler.visibility = View.VISIBLE
                progressB.visibility = View.GONE
            }, {
                Toast.makeText(activity, "You have no internet!", Toast.LENGTH_LONG).show()
            })


        return asd
    }
}