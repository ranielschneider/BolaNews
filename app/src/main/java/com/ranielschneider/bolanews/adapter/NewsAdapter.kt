package com.ranielschneider.bolanews.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ranielschneider.bolanews.R
import com.seuapp.futebolnews.model.NewsItem



class NewsAdapter(
    private var items: List<NewsItem>,
    private val onItemClick: (NewsItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val TYPE_HIGHLIGHT = 0
        const val TYPE_GRID_ROW  = 1
        const val TYPE_LIST      = 2
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0    -> TYPE_HIGHLIGHT
            1    -> TYPE_GRID_ROW
            else -> TYPE_LIST
        }
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HIGHLIGHT -> HighlightViewHolder(
                inflater.inflate(R.layout.item_news_highlight, parent, false)
            )
            TYPE_GRID_ROW -> GridRowViewHolder(
                inflater.inflate(R.layout.item_news_grid_row, parent, false)
            )
            else -> ListViewHolder(
                inflater.inflate(R.layout.item_news_list, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HighlightViewHolder -> holder.bind(items[position])
            is GridRowViewHolder   -> holder.bind(items[position], items.getOrNull(position + 1))
            is ListViewHolder      -> holder.bind(items[position])
        }
    }

    fun updateList(newItems: List<NewsItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    // ── ViewHolder 1: Destaque ────────────────────────────────────────────────
    inner class HighlightViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvCategory: TextView = view.findViewById(R.id.tvHighlightCategory)
        private val tvTitle:    TextView = view.findViewById(R.id.tvHighlightTitle)
        private val tvMeta:     TextView = view.findViewById(R.id.tvHighlightMeta)

        fun bind(item: NewsItem) {
            tvCategory.text = item.category.uppercase()
            tvTitle.text    = item.title
            tvMeta.text     = item.source + " · " + item.timeAgo
            itemView.setOnClickListener { onItemClick(item) }
        }
    }

    // ── ViewHolder 2: Grid duplo ──────────────────────────────────────────────
    inner class GridRowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cardLeft:  ViewGroup = view.findViewById(R.id.cardGridLeft)
        private val cardRight: ViewGroup = view.findViewById(R.id.cardGridRight)

        fun bind(left: NewsItem, right: NewsItem?) {
            bindCard(cardLeft, left)
            cardLeft.setOnClickListener { onItemClick(left) }

            if (right != null) {
                cardRight.visibility = View.VISIBLE
                bindCard(cardRight, right)
                cardRight.setOnClickListener { onItemClick(right) }
            } else {
                cardRight.visibility = View.INVISIBLE
            }
        }

        private fun bindCard(card: ViewGroup, item: NewsItem) {
            (card.getChildAt(1) as? ViewGroup)?.let { textGroup ->
                (textGroup.getChildAt(0) as? TextView)?.text = item.category.uppercase()
                (textGroup.getChildAt(1) as? TextView)?.text = item.title
                (textGroup.getChildAt(2) as? TextView)?.text = item.source + " · " + item.timeAgo
            }
        }
    }

    // ── ViewHolder 3: Lista compacta ──────────────────────────────────────────
    inner class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvCategory: TextView = view.findViewById(R.id.tvListCategory)
        private val tvTitle:    TextView = view.findViewById(R.id.tvListTitle)
        private val tvMeta:     TextView = view.findViewById(R.id.tvListMeta)

        fun bind(item: NewsItem) {
            tvCategory.text = item.category.uppercase()
            tvTitle.text    = item.title
            tvMeta.text     = item.source + " · " + item.timeAgo
            itemView.setOnClickListener { onItemClick(item) }
        }
    }
}