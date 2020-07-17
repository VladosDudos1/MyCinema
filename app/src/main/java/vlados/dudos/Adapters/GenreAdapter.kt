package vlados.dudos.Adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.ganre_view.view.*
import vlados.dudos.Case.genreId
import vlados.dudos.Models.Genre
import vlados.dudos.R

class GenreAdapter(
    val list: List<Genre>,
    val onClickListener: OnClickListener,
    val context: Context
) : RecyclerView.Adapter<GenreAdapter.GenreView>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreView {
        return GenreView(
            LayoutInflater.from(parent.context).inflate(
                R.layout.ganre_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: GenreView, position: Int) {
        holder.itemView.ganre_txt.text = list[position].name.capitalize()

        holder.itemView.ganre_txt.setOnClickListener {
            onClickListener.click(list[position])
            genreId = list[position].id

        }

    }

    class GenreView(view: View) : RecyclerView.ViewHolder(view)

    interface OnClickListener {
        fun click(data: Genre)
    }
}