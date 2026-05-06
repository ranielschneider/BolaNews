package com.ranielschneider.bolanews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ranielschneider.bolanews.R
import com.ranielschneider.bolanews.model.NewsItem

class NewsAdapter(
    private var items: List<NewsItem>,
    private val onItemClick: (NewsItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HIGHLIGHT = 0
        const val TYPE_GRID = 1
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HIGHLIGHT else TYPE_GRID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            TYPE_HIGHLIGHT -> HighlightViewHolder(
                inflater.inflate(R.layout.item_news_highlight, parent, false)
            )
            else -> GridViewHolder(
                inflater.inflate(R.layout.item_news_grid, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]

        when (holder) {
            is HighlightViewHolder -> holder.bind(item)
            is GridViewHolder -> holder.bind(item)
        }
    }

    fun updateList(newItems: List<NewsItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    inner class HighlightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivImage: ImageView = view.findViewById(R.id.ivHighlightImage)
        private val tvCategory: TextView = view.findViewById(R.id.tvHighlightCategory)
        private val tvTitle: TextView = view.findViewById(R.id.tvHighlightTitle)
        private val tvMeta: TextView = view.findViewById(R.id.tvHighlightMeta)

        fun bind(item: NewsItem) {
            tvCategory.text = item.category.uppercase()
            tvTitle.text = item.title
            tvMeta.text = "${item.source} · ${item.timeAgo}"

            Glide.with(itemView)
                .load(item.imageUrl)
                .placeholder(R.color.image_placeholder)
                .centerCrop()
                .into(ivImage)

            itemView.setOnClickListener { onItemClick(item) }
        }
    }

    inner class GridViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivImage: ImageView = view.findViewById(R.id.ivGridImage)
        private val tvCategory: TextView = view.findViewById(R.id.tvGridCategory)
        private val tvTitle: TextView = view.findViewById(R.id.tvGridTitle)
        private val tvMeta: TextView = view.findViewById(R.id.tvGridMeta)

        fun bind(item: NewsItem) {
            tvCategory.text = item.category.uppercase()
            tvTitle.text = item.title
            tvMeta.text = "${item.source} · ${item.timeAgo}"

            Glide.with(itemView)
                .load(item.imageUrl)
                .placeholder(R.color.image_placeholder)
                .centerCrop()
                .into(ivImage)

            itemView.setOnClickListener { onItemClick(item) }
        }
    }
}