package com.example.carousels

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carousels.CarouselsApplication.Companion.context
import com.squareup.picasso.Picasso

class HorizontalAdapter(val type: String, val items: List<Item>, context: Context) :
    RecyclerView.Adapter<ItemViewHolder>() {

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(type, items[position], position, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class ItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item, parent, false)) {
    private var mTitleView: TextView? = null
    private var mImageView: ImageView? = null

    init {
        mTitleView = itemView.findViewById(R.id.caption)
        mImageView = itemView.findViewById(R.id.image)
    }

    fun bind(type: String, item: Item, i: Int, context: Context) {
        val categories = arrayOf("nature", "animals", "tech", "arch", "people", "grayscale", "sepia")
        val index = i % categories.size
        if (type == "thumb") {
            mTitleView?.setBackgroundColor(context.getColor(R.color.gray))
            mTitleView?.setTextColor(context.getColor(R.color.black))
        }
        mTitleView?.text = item.title
        var url = when {
            item.url.isNotEmpty() -> item.url
            type == "poster" -> "http://placeimg.com/320/480/${categories[index]}"
            else -> "http://placeimg.com/640/480/${categories[index]}"
        }
        Picasso.get().load(url).into(mImageView)
    }
}