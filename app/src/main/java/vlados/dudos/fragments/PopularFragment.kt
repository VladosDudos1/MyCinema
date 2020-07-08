package vlados.dudos.fragments

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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.popular_fragment.view.*
import vlados.dudos.Adapters.RvAdapterPopular
import vlados.dudos.InfoActivity
import vlados.dudos.Models.Result
import vlados.dudos.R
import vlados.dudos.app.App

class PopularFragment : Fragment(), RvAdapterPopular.OnClickListener, TextWatcher {

    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }


    override fun click(data: Result){
        startActivity(Intent(activity, InfoActivity::class.java))
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val asd = inflater.inflate(R.layout.popular_fragment, container, false)
        val recycler = asd.rv_popular
        val progressB = asd.pb_popular


        val disp = App.dm.api
            .getPopular()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ p ->
                recycler.layoutManager =
                    LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                recycler.adapter = RvAdapterPopular(p.results, this)

                recycler.visibility = View.VISIBLE
                progressB.visibility = View.GONE
            }, {
                Toast.makeText(activity, "You have no internet!", Toast.LENGTH_LONG).show()
            })

        return asd
    }
}

