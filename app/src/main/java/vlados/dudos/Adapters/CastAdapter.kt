package vlados.dudos.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import kotlinx.android.synthetic.main.cast_view.view.*
import vlados.dudos.Models.Cast
import vlados.dudos.R

class CastAdapter(val list: List<Cast>) : RecyclerView.Adapter<CastAdapter.CastHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cast_view, parent,false)
        return CastHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: CastHolder, position: Int) {
        Glide.with(holder.itemView.img_actor)
            .load("https://image.tmdb.org/t/p/w1280"+ list[position].profile_path)
            .error(R.drawable.noimage)
            .into(holder.itemView.img_actor)
        holder.itemView.name_actor.text = list[position].name
        holder.itemView.role_actor.text = list[position].character
    }

    class CastHolder(view: View): RecyclerView.ViewHolder(view)
}