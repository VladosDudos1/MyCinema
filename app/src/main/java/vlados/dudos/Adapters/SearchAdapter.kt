package vlados.dudos.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_view.view.*
import kotlinx.android.synthetic.main.search_element.view.*
import vlados.dudos.Case
import vlados.dudos.Models.Result
import vlados.dudos.R

class SearchAdapter(val list: List<Result>, val onClickListener: OnClickListener): RecyclerView.Adapter<SearchAdapter.SearchView>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchView {
        return SearchView(LayoutInflater.from(parent.context).inflate(R.layout.search_element, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: SearchView, position: Int) {
        Glide.with(holder.itemView.img_search)
            .load("https://image.tmdb.org/t/p/w1280"+ list[position].poster_path)
            .error(R.drawable.noimage)
            .into(holder.itemView.img_search)

        holder.itemView.main_card.setOnClickListener {
            onClickListener.click(list[position])
            Case.item = list[position]
            Case.id = list[position].id
        }


        holder.itemView.name_search.text = list[position].title
        holder.itemView.plot_search.text = list[position].overview
    }

    class SearchView(view: View): RecyclerView.ViewHolder(view)

    interface OnClickListener {
        fun click(data: Result)
    }
}