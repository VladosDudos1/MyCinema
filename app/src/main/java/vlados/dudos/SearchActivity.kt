package vlados.dudos

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_search.*
import vlados.dudos.Adapters.SearchAdapter
import vlados.dudos.Models.Result
import vlados.dudos.app.App



class SearchActivity : AppCompatActivity(), TextWatcher, SearchAdapter.OnClickListener {

    override fun click(data: Result){
        startActivity(Intent(this, InfoActivity::class.java))
    }


    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
       val disp = App.dm.api
            .search(edit_text.text.toString())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({s ->
                pb_search.visibility =View.GONE
                rv_search.layoutManager = LinearLayoutManager(this)
                rv_search.adapter = SearchAdapter(s.results, this)

                rv_search.visibility = View.VISIBLE
                pb_search.visibility = View.GONE
            }, {
                Log.d("", "")
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)


        done_card.setOnClickListener {
            val view = this.currentFocus
            if (view != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        edit_text.addTextChangedListener(this)

        backarrow.setOnClickListener {
            super.onBackPressed()
        }
    }
}
