package com.ranielschneider.bolanews

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ranielschneider.bolanews.R
import com.ranielschneider.bolanews.adapter.NewsAdapter
import com.seuapp.futebolnews.model.NewsItem

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: NewsAdapter
    private lateinit var recyclerNews: RecyclerView
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var tvSelectedTeam: TextView
    private lateinit var layoutTeamFilter: android.view.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupRecyclerView()
        loadFakeData()  // <- trocaremos por API mais pra frente
    }

    private fun setupViews() {
        recyclerNews    = findViewById(R.id.recyclerNews)
        swipeRefresh    = findViewById(R.id.swipeRefresh)
        tvSelectedTeam  = findViewById(R.id.tvSelectedTeam)
        layoutTeamFilter = findViewById(R.id.layoutTeamFilter)

        // Cor do indicador de refresh
        swipeRefresh.setColorSchemeResources(R.color.green_primary)

        // Recarrega ao puxar para baixo
        swipeRefresh.setOnRefreshListener {
            loadFakeData()
            swipeRefresh.isRefreshing = false
        }

        // Abre seleção de time ao clicar no chip
        layoutTeamFilter.setOnClickListener {
            showTeamPicker()
        }
    }

    private fun setupRecyclerView() {
        adapter = NewsAdapter(
            items = emptyList(),
            onItemClick = { news ->
                // TODO: abrir tela de detalhe ou browser com news.articleUrl
            }
        )
        recyclerNews.layoutManager = LinearLayoutManager(this)
        recyclerNews.adapter = adapter
    }

    private fun loadFakeData() {
        // Dados de teste — serão substituídos pela chamada real de API
        val fakeNews = listOf(
            NewsItem(
                id = "1",
                title = "Flamengo confirma contratação de atacante por R$ 40 milhões",
                category = "Transferências",
                source = "GloboEsporte",
                timeAgo = "1h atrás",
                imageUrl = "",
                articleUrl = "",
                team = "Flamengo"
            ),
            NewsItem(
                id = "2",
                title = "Corinthians monitora volante argentino para o segundo semestre",
                category = "Transferências",
                source = "Lance",
                timeAgo = "4h atrás",
                imageUrl = "",
                articleUrl = "",
                team = "Corinthians"
            ),
            NewsItem(
                id = "3",
                title = "Técnico testa novo esquema tático antes da rodada",
                category = "Treino",
                source = "ESPN",
                timeAgo = "6h atrás",
                imageUrl = "",
                articleUrl = ""
            ),
            NewsItem(
                id = "4",
                title = "Brasileirão rodada 12: veja todos os resultados",
                category = "Brasileirão",
                source = "ge.globo",
                timeAgo = "8h atrás",
                imageUrl = "",
                articleUrl = ""
            ),
            NewsItem(
                id = "5",
                title = "Libertadores: semifinais têm datas e horários confirmados",
                category = "Libertadores",
                source = "UOL",
                timeAgo = "10h atrás",
                imageUrl = "",
                articleUrl = ""
            ),
            NewsItem(
                id = "6",
                title = "Seleção Brasileira: Dorival convoca para as Eliminatórias",
                category = "Seleção",
                source = "ge.globo",
                timeAgo = "12h atrás",
                imageUrl = "",
                articleUrl = ""
            )
        )
        adapter.updateList(fakeNews)
    }

    private fun showTeamPicker() {
        // Lista de times para filtrar
        val teams = arrayOf(
            "Todos os times",
            "Flamengo", "Corinthians", "Palmeiras",
            "São Paulo", "Santos", "Grêmio",
            "Internacional", "Atlético-MG", "Fluminense", "Vasco"
        )

        android.app.AlertDialog.Builder(this)
            .setTitle("Filtrar por time")
            .setItems(teams) { _, index ->
                val selected = teams[index]
                tvSelectedTeam.text = selected

                // Filtra as notícias pelo time escolhido
                filterByTeam(if (index == 0) "" else selected)
            }
            .show()
    }

    private fun filterByTeam(team: String) {
        // Por enquanto filtra os dados fake — a lógica será a mesma com a API
        val allNews = (recyclerNews.adapter as NewsAdapter)
        if (team.isEmpty()) {
            loadFakeData()  // sem filtro: mostra tudo
        } else {
            // TODO: quando integrar API, passar o time como parâmetro da busca
        }
    }
}