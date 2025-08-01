# Desafio: Explorador de Filmes

Este repositório contém a solução para o Desafio Técnico de Desenvolvedor Android Júnior. O objetivo foi criar um aplicativo nativo para Android que consome a API do The Movie Database (TMDB) para explorar filmes, seguindo as melhores práticas de arquitetura e desenvolvimento moderno. 

## Screenshots
| Tela Inicial | Filmes Populares | Detalhes do Filme 1 | Detalhes do Filme 2 |
| :---: | :---: | :---: | :---: |
| ![Tela Inicial com Paginação](https://drive.google.com/uc?export=view&id=1T8vy3i0jvJCPLYMmqpVzUadePVqycFYT) | ![Tela de Filmes Populares](https://drive.google.com/uc?export=view&id=114KkRiVsS-aevqrtavDqPIDN6taAQ6C8) | ![Detalhes do Filme 1](https://drive.google.com/uc?export=view&id=1958-LOXRVfwlRt1wsE4qyTN8TrU3_0pb) | ![Detalhes do Filme 2](https://drive.google.com/uc?export=view&id=18-Q8E39qPrPsBs3S9Yh55KXpBJPw1_Tk) |


## Funcionalidades Implementadas

### Requisitos Obrigatórios
- [x]  **Tela Inicial:** Exibe uma lista de filmes do endpoint "Discover" da API, com paginação de scroll infinito. 
    - [x]  Título do filme. 
    - [x]  Imagem do pôster. 
    - [x]  Data de lançamento formatada. 
- [x]  **Tela de Detalhes:** Exibe informações completas sobre um filme selecionado.
    - [x]  Título do filme. 
    - [x]  Imagem do pôster. 
    - [x]  Data de lançamento formatada. 
    - [x]  Descrição (overview). 
- [x]  **Consumo de API REST:** Utiliza a API pública do TMDB. 
- [x]  **Uso de `LazyColumn` (Jetpack Compose):** A lista de filmes é exibida usando `LazyColumn`, o equivalente moderno e recomendado ao `RecyclerView` no Jetpack Compose. 
- [x]  **Persistência Mínima de Estado:** Os dados são mantidos durante a rotação da tela e outras mudanças de configuração através do uso de um `ViewModel`. 

### Diferenciais Implementados
- [x]  **Uso de Arquitetura MVVM:** O projeto segue estritamente a arquitetura Model-View-ViewModel para uma clara separação de responsabilidades.
- [x]  **Utilização de Jetpack Components:**
    - [x]  **ViewModel:** Para gerenciar o estado e a lógica da UI.
    - [x]  **Navigation Compose:** Para gerenciar a navegação entre as telas de forma declarativa.
    - [x]  **StateFlow & LiveData:** Para expor o estado do ViewModel para a UI de forma reativa.
- [x]  **Paginação da Lista de Filmes:** Implementação de paginação manual com scroll infinito para as listas "Discover" e "Popular", garantindo performance e eficiência no uso de dados.
- [x]  **Tratamento de Erros de Rede:** A UI reage a falhas na rede, exibindo uma mensagem de erro e um botão "Tentar Novamente" para o carregamento inicial das listas.
- [x]  **Layout Responsivo e Bom UX:**
    - [x]  O uso de `Modifier.weight(1f)` garante que a lista ocupe o espaço disponível de forma responsiva.
    - [x]  A UI fornece feedback claro para estados de carregamento, erro e listas vazias.
    - [x]  A busca de filmes utiliza "debounce" para uma experiência fluida e para evitar chamadas excessivas à API.
- [x]  **Utilização de Coil:** Biblioteca moderna utilizada para o carregamento assíncrono e eficiente de imagens a partir da URL.
- [x]  **Adição de Logs:** O `HttpLoggingInterceptor` foi configurado no cliente OkHttp para inspecionar todas as chamadas de rede, facilitando a depuração.

### O que Ficou Pendente
- 🔴  **Testes unitários simples:** Devido ao foco na implementação completa das funcionalidades e na robustez da arquitetura da UI, os testes unitários não foram implementados.

---
## 🛠 Tecnologias e Arquitetura

O aplicativo foi desenvolvido utilizando **100% Kotlin** e **Jetpack Compose** para a interface do usuário. 

A arquitetura é baseada em **MVVM (Model-View-ViewModel)**:
* **Model:** O pacote `data` contém o `Repository`, os modelos de dados e a camada de acesso à rede com **Retrofit** e **OkHttp**. 
* **View:** O pacote `ui` contém todos os **Composables** que constroem a UI, além da `MainActivity`. A UI é reativa e observa as mudanças de estado do ViewModel.
* **ViewModel:** O `MoviesViewModel` serve como intermediário, contendo toda a lógica de apresentação e gerenciando o estado da UI com **StateFlow**. Operações assíncronas são gerenciadas com **Coroutines**.

---
##  Instruções de Build e Execução (Build/Run)

### Pré-requisitos
- Android Studio (versão mais recente recomendada)
- JDK 11 ou superior

### Passos para Executar

1.  **Clone o repositório:**
    ```bash
    git clone [URL_DO_SEU_REPOSITORIO]
    ```
2.  **Obtenha uma Chave da API (API Key):**
    - Crie uma conta no site [The Movie Database (TMDB)](https://www.themoviedb.org/). 
    - Nas configurações da sua conta, vá para a seção "API" e solicite uma chave (API Key v3 Auth).

3.  **Adicione a Chave da API ao Projeto:**
    - Na pasta raiz do projeto, localize ou crie um arquivo chamado `gradle.properties` ou `local.properties`(mais recomendado).
    - Adicione a seguinte linha a este arquivo, substituindo `SUA_CHAVE_AQUI` pela chave que você obteve:
      ```properties
      TMDB_API_KEY="SUA_CHAVE_AQUI"
      ```
    - **Importante:** O arquivo `local.properties` já está incluído no `.gitignore` para garantir que sua chave secreta não seja enviada para o repositório.

4.  **Abra e Execute no Android Studio:**
    - Abra o projeto no Android Studio.
    - Aguarde o Gradle sincronizar as dependências.
    - Compile e execute o aplicativo em um emulador ou dispositivo físico.

