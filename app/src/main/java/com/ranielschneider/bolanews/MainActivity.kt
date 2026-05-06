package com.ranielschneider.bolanews

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ranielschneider.bolanews.adapter.NewsAdapter
import com.ranielschneider.bolanews.model.NewsItem
import com.ranielschneider.bolanews.repository.NewsRepository
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NewsAdapter
    private lateinit var recyclerNews: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var tvSelectedTeam: TextView
    private lateinit var layoutTeamFilter: View
    private lateinit var layoutEmpty: View

    private val repository = NewsRepository()
    private var selectedTeam = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupRecyclerView()
        loadNews()
    }

    private fun setupViews() {
        recyclerNews = findViewById(R.id.recyclerNews)
        swipeRefresh = findViewById(R.id.swipeRefresh)
        tvSelectedTeam = findViewById(R.id.tvSelectedTeam)
        layoutTeamFilter = findViewById(R.id.layoutTeamFilter)
        layoutEmpty = findViewById(R.id.layoutEmpty)

        swipeRefresh.setColorSchemeResources(R.color.green_primary)
        swipeRefresh.setOnRefreshListener { loadNews() }
        layoutTeamFilter.setOnClickListener { showTeamPicker() }
    }

    private fun setupRecyclerView() {
        adapter = NewsAdapter(
            items = emptyList<NewsItem>(),
            onItemClick = { news ->
                openNewsUrl(news.articleUrl)
            }
        )

        val layoutManager = GridLayoutManager(this, 2)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == 0) 2 else 1
            }
        }

        recyclerNews.layoutManager = layoutManager
        recyclerNews.adapter = adapter
        recyclerNews.layoutAnimation = android.view.animation.AnimationUtils.loadLayoutAnimation(
            this,
            R.anim.layout_animation_fall_down
        )
    }

    private fun openNewsUrl(url: String) {
        val customTabsIntent = CustomTabsIntent.Builder()
            .setShowTitle(true)
            .setUrlBarHidingEnabled(true)
            .setToolbarColor(
                ContextCompat.getColor(
                    this,
                    R.color.green_primary
                )
            )
            .build()

        customTabsIntent.launchUrl(this, Uri.parse(url))
    }

    private fun loadNews() {
        swipeRefresh.isRefreshing = true
        layoutEmpty.visibility = View.GONE

        lifecycleScope.launch {
            try {
                val news = repository.getFootballNews(selectedTeam)
                adapter.updateList(news)
                recyclerNews.scheduleLayoutAnimation()
                layoutEmpty.visibility = if (news.isEmpty()) View.VISIBLE else View.GONE
            } catch (e: Exception) {
                layoutEmpty.visibility = View.VISIBLE
            } finally {
                swipeRefresh.isRefreshing = false
            }
        }
    }

    private fun showTeamPicker() {
        val teams = arrayOf(
            "Todos os times",
            "Flamengo",
            "Corinthians",
            "Palmeiras",
            "São Paulo",
            "Santos",
            "Grêmio",
            "Internacional",
            "Atlético-MG",
            "Fluminense",
            "Vasco"
        )

        AlertDialog.Builder(this)
            .setTitle("Filtrar por time")
            .setItems(teams) { _, index ->
                selectedTeam = if (index == 0) "" else teams[index]
                tvSelectedTeam.text = teams[index]
                loadNews()
            }
            .show()
    }
}