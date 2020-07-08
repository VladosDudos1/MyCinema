package vlados.dudos.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_view.view.*
import vlados.dudos.Case
import vlados.dudos.Case.item
import vlados.dudos.Models.Result
import vlados.dudos.R
import java.util.*


class RvAdapterPopular(val list: List<Result>, val onClickListener: OnClickListener) : RecyclerView.Adapter<RvAdapterPopular.RvViewP>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewP {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_view, parent,  false)
        return RvViewP(view)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: RvViewP, position: Int) {
        holder.itemView.text.text = list[position].title
        Glide.with(holder.itemView.img)
            .load("https://image.tmdb.org/t/p/w1280"+ list[position].poster_path)
            .error(R.drawable.noimage)
            .into(holder.itemView.img)
        holder.itemView.raiting.text = list[position].vote_average.toString()

        holder.itemView.main_layout.setOnClickListener {
            onClickListener.click(list[position])
            item = list[position]
            Case.id = list[position].id
        }
    }

    class RvViewP(view: View): RecyclerView.ViewHolder(view)

    interface OnClickListener {
        fun click(data: Result)
    }
}