# BolaNews ⚽

Aplicativo Android de notícias de futebol brasileiro, com destaque para as principais manchetes e filtro por time do coração.

## ✨ Funcionalidades

- Feed de notícias sobre futebol, com categorização automática (Transferências, Brasileirão, Libertadores, Seleção, Copa)
- Layout em grid dinâmico, com a primeira notícia em destaque ocupando duas colunas
- Filtro por time (Flamengo, Corinthians, Palmeiras, São Paulo, Santos, Grêmio, Internacional, Atlético-MG, Fluminense, Vasco)
- Pull-to-refresh para atualizar as notícias
- Animação de entrada dos cards na lista
- Abertura das notícias completas via Chrome Custom Tabs, sem sair do app
- Estado vazio tratado quando não há resultados para o filtro

## 🛠️ Tecnologias

- **Kotlin**
- **RecyclerView** com `GridLayoutManager` (span customizado para o card de destaque)
- **Retrofit + Gson** — consumo da API REST
- **SwipeRefreshLayout** — pull-to-refresh
- **Chrome Custom Tabs** — abertura das notícias completas
- API consumida: [NewsAPI](https://newsapi.org/) (endpoint `/v2/everything`)

## 🚀 Como rodar o projeto

1. Clone o repositório:
   ```bash
   git clone https://github.com/ranielschneider/BolaNews.git
   ```
2. Crie uma conta gratuita na [NewsAPI](https://newsapi.org/) e gere sua própria API key.
3. Abra no Android Studio e rode em um emulador ou dispositivo físico.

> ⚠️ **Nota de segurança:** a versão atual do projeto tem a API key da NewsAPI diretamente no código-fonte (`NewsRepository.kt`), por ser um projeto de estudo. Para uso real, mova essa chave para `local.properties` + `BuildConfig`, evitando expor credenciais em repositórios públicos.

## 📖 Sobre o projeto

Projeto desenvolvido para praticar consumo de API REST, RecyclerView com layouts variados no mesmo grid, e navegação assistida (Custom Tabs) em Android.

## 👤 Autor

**Raniel Schneider**
[GitHub](https://github.com/ranielschneider) · [LinkedIn](https://linkedin.com/in/raniel-schneider-79006b50)
