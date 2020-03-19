package com.delarosa.prueba.ui.icecream

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.delarosa.domain.IceCream
import com.delarosa.prueba.R
import kotlinx.android.synthetic.main.ice_cream_item.view.*

class IceCreamAdapter(private val listener: (IceCream) -> Unit) :
    RecyclerView.Adapter<IceCreamAdapter.ViewHolder>() {

    private var iceCreams: MutableList<IceCream> = mutableListOf()

    fun appendItems(newItems: List<IceCream>) {
        iceCreams.clear()
        iceCreams.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.ice_cream_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = iceCreams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ice = iceCreams[position]
        holder.bind(ice)
        holder.itemView.setOnClickListener { listener(ice) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(iceCream: IceCream) {
            itemView.tittle.text = iceCream.name1
            itemView.image.background.setColorFilter(Color.parseColor(iceCream.bg_color), PorterDuff.Mode.SRC_ATOP)
            itemView.image.setImage(iceCream.type)
            itemView.price.text = iceCream.price
        }
    }
}

fun ImageView.setImage(url: String) {
    apply {
        setImageResource(context.resources.getIdentifier(url, "drawable", context.packageName))
    }
}