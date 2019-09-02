package com.example.carousels

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ListAdapter(val carousels: List<Carousel>) : RecyclerView.Adapter<CarouselViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CarouselViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        holder.bind(carousels[position])
    }

    override fun getItemCount(): Int = carousels.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
}

class CarouselViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.horizontal_recycler_view, parent, false)) {
    private var mTitleView: TextView? = null
    private var mRecyclerView: RecyclerView? = null

    init {
        mTitleView = itemView.findViewById(R.id.title)
        mRecyclerView = itemView.findViewById(R.id.horizontal_recycler_view)

    }

    fun bind(item: Carousel) {
        mTitleView?.text = item.title
        mRecyclerView?.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = HorizontalAdapter(item.type, item.items,context)
        }
    }
}

