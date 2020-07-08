package vlados.dudos.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.movie_view.view.*
import vlados.dudos.Case
import vlados.dudos.Case.id
import vlados.dudos.Models.Result
import vlados.dudos.R


class RvSoonAdapter (val list: List<Result>, val onClickListener: OnClickListener) : RecyclerView.Adapter<RvSoonAdapter.RvViewS>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewS {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_view, parent,  false)
        return RvViewS(view)
    }

    override fun getItemCount(): Int = list.size


    override fun onBindViewHolder(holder: RvViewS, position: Int) {
        holder.itemView.text.text = list[position].title
        Glide.with(holder.itemView.img)
            .load("https://image.tmdb.org/t/p/w1280"+ list[position].poster_path)
            .error(R.drawable.noimage)
            .into(holder.itemView.img)
        holder.itemView.raiting.text = list[position].vote_average.toString()

        holder.itemView.main_layout.setOnClickListener {
            onClickListener.click(list[position])
            Case.item = list[position]
            id = list[position].id
        }


    }

    class RvViewS(view: View): RecyclerView.ViewHolder(view)

    interface OnClickListener{
        fun click(data: Result)
    }
}