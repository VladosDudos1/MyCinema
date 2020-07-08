package vlados.dudos.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.ganre_view.view.*
import vlados.dudos.Models.Genre
import vlados.dudos.R

class GenreAdapter (val list: List<Genre>): RecyclerView.Adapter<GenreAdapter.GenreView>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreView {
        return GenreView(LayoutInflater.from(parent.context).inflate(R.layout.ganre_view, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: GenreView, position: Int) {
        holder.itemView.ganre_txt.text = list[position].name
    }

    class GenreView(view: View): RecyclerView.ViewHolder(view)
}