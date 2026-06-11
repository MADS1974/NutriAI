# 🥗 NutrIA — Assistente de Nutrição Inteligente

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Room](https://img.shields.io/badge/Database-Room-00BCD4?style=for-the-badge)
![Gemini AI](https://img.shields.io/badge/AI-Google%20Gemini-121011?style=for-the-badge&logo=googlegemini&logoColor=white)

O **NutrIA** é um assistente de nutrição inteligente desenvolvido nativamente para a plataforma Android. O aplicativo permite que os usuários registrem suas refeições de forma simples e rápida através de fotos (capturadas pela câmera ou selecionadas da galeria) ou descrições em texto. 

Esses dados são processados e enviados para a inteligência artificial do Google (Gemini), que realiza uma análise detalhada contendo a estimativa de calorias, divisão de macronutrientes (carboidratos, proteínas e gorduras) e dicas personalizadas de saúde. Para garantir a autonomia do usuário, todos os registros e análises são persistidos localmente.

---

## 🎯 Funcionalidades Principais

* **Registro Multimodal:** Envio de texto, imagem ou ambos combinados para uma análise altamente precisa.
* **Análise Inteligente (IA):** Integração direta com os modelos mais recentes do Google Gemini para extração de dados nutricionais.
* **Persistência Local:** Histórico completo de refeições salvo de forma segura no dispositivo.
* **Interface Reativa:** Tela principal (Dashboard) atualizada em tempo real assim que um novo registro é concluído.
* **Limpeza de Histórico:** Opção de exclusão rápida de dados para gerenciamento e demonstrações do aplicativo.

---

## 🛠️ Pilares Tecnológicos (Stack)

* **Linguagem:** [Kotlin](https://kotlinlang.org/) (Desenvolvimento Android moderno).
* **Interface Gráfica:** [Jetpack Compose](https://developer.android.com/jetpack/compose) para uma interface de usuário moderna, fluida e declarativa.
* **Arquitetura:** **MVVM (Model-View-ViewModel)**, promovendo a separação de conceitos e facilitando a testabilidade.
* **Inteligência Artificial:** [SDK Google Generative AI](https://ai.google.dev/) utilizando o modelo otimizado `gemini-3.1-flash-lite` (ou `gemini-3.5-flash`).
* **Banco de Dados:** **Room Database** para abstração e persistência local sobre o SQLite.
* **Processamento de Imagem:** * [Coil](https://coil-api.github.io/coil/) para carregamento assíncrono e eficiente de imagens na interface.
    * *Bitmap Scaling* para otimização do tamanho das imagens antes do envio para a API de IA, economizando largura de banda e tempo de processamento.
* **Injeção/Gerenciamento de Ciclo de Vida:** `AndroidViewModel` (Context-aware) para gerenciar com segurança o ciclo de vida e o acesso ao contexto da aplicação.

---

## 📂 Estrutura do Projeto (Principais Componentes)

O projeto está estruturado seguindo boas práticas de arquitetura em camadas:

### 🧬 A. Camada de Dados (`Data`)
* **`Refeicao.kt` (Entity):** Define a estrutura da tabela no banco de dados SQLite contendo: `id`, `descricao`, `analiseIA` e `data` (armazenada como *timestamp*).
* **`RefeicaoDao.kt` (DAO):** Interface que mapeia as consultas SQL para métodos Kotlin:
    * `inserir()`: Salva uma nova análise nutricional.
    * `listarTodas()`: Retorna um `Flow` reativo contendo o histórico atualizado em tempo real.
    * `deletarTudo()`: Remove todos os registros (ideal para reset de demonstração).
* **`NutriDatabase.kt`:** Classe abstrata baseada no padrão *Singleton* que gerencia a instância única do banco de dados Room.

### 🎨 B. Camada de Interface (`UI`)
* **`NutriViewModel.kt`:** O componente central do aplicativo. Controla os estados da tela (*Loading*, *Success*, *Error*), processa as chamadas assíncronas à API do Gemini e coordena o salvamento dos dados gerados no Room.
* **`DashboardScreen.kt`:** Tela principal do usuário que exibe o resumo dos registros diários, a lista de refeições recentes em ordem cronológica e o botão de limpeza rápida (lixeira).
* **`RegistroScreen.kt`:** Tela de captura onde o usuário interage com a câmera/galeria e insere as informações textuais da refeição.

---

## 🛡️ Histórico de Engenharia e Soluções (Troubleshooting)

Durante o ciclo de desenvolvimento, desafios técnicos de engenharia de software e compatibilidade de SDKs foram superados:

### 🏗️ Compilação e SDK Atualizado
* **Configuração para o SDK 36:** O projeto foi atualizado para atingir o ecossistema do SDK Android 36, atendendo aos requisitos rigorosos das bibliotecas de navegação e do núcleo nativo.
* **Migração para o KSP (Kotlin Symbol Processing):** Substituição do legado `annotationProcessor`/`kapt` pelo **KSP**, acelerando o tempo de compilação e garantindo a geração correta do código interno do Room.

### 🤖 Ajustes na API do Gemini
* **Tratamento de Erros 404/503:** Superados por meio do mapeamento e atualização dos nomes dos modelos para as versões estáveis (`gemini-3.1-flash-lite` ou `gemini-3.5-flash`), garantindo alta disponibilidade e respostas rápidas.
* **Configuração Multimodal:** Ajuste fino no pipeline de dados para permitir o envio simultâneo de texto brutos e arrays de bytes de imagens (Bitmaps), elevando o nível de contexto fornecido à IA.

### 🗄️ Estabilidade do Banco de Dados
* **Mitigação de Erros de Migração (Migration Error):** Resolvido através da implementação de estratégias de "instalação limpa" (*Clean/Rebuild* combinado com a reinicialização física do app no emulador/dispositivo), garantindo a atualização do esquema da entidade sem quebras de execução.
* **Reatividade com Flow & StateIn:** Implementação de fluxos assíncronos frios convertidos em estados quentes (`StateIn`), fazendo com que o `DashboardScreen` reaja automaticamente a novas inserções no banco sem necessidade de requisições manuais (*pull-to-refresh*).

---

## 📱 Como Operar o Aplicativo (Roteiro de Demonstração)

Siga estes passos para realizar uma demonstração completa das capacidades do NutrIA:

1.  **Limpar o Ambiente:** Na tela principal (Dashboard), clique no ícone da lixeira para zerar o banco de dados e iniciar o teste com 0 registros.
2.  **Registrar a Refeição:** Navegue até a tela "Registrar Refeição". Você pode tirar uma foto do seu prato ou simplesmente digitar uma descrição detalhada como: *“Almoço: 200g de arroz integral, 100g de feijão preto e 150g de filé de frango grelhado”*.
3.  **Acompanhar a Análise:** Observe o indicador de progresso (*CircularProgressIndicator*) na tela enquanto a SDK envia os dados ao Gemini e aguarda o retorno estruturado.
4.  **Verificar a Persistência:** Ao retornar para o Dashboard, a refeição aparecerá listada com o horário exato e o resumo calórico. Feche o aplicativo completamente e abra-o novamente para comprovar que os dados continuam salvos e seguros localmente.

---

## 🚀 Próximos Passos (Roadmap)

* [ ] **Filtros Temporais Avançados:** Segmentar a visualização do histórico para exibir apenas consumos feitos "hoje", "esta semana" ou "este mês".
* [ ] **Indicadores Visuais Gráficos:** Implementar componentes personalizados com a API `Canvas` do Jetpack Compose para renderizar gráficos de pizza/rosca ilustrando a distribuição percentual de macronutrientes.
* [ ] **Exportação de Relatórios:** Desenvolvimento de uma rotina para gerar PDFs semanais contendo as métricas e os principais conselhos da IA, facilitando o compartilhamento com nutricionistas profissionais.

---

Desenvolvido com ❤️ como projeto prático para a plataforma Android.

---


## 📱 Demonstração em Vídeo
Confira o aplicativo em funcionamento:

🎥 **[CLIQUE AQUI PARA VER O VÍDEO DO APP](https://youtube.com/shorts/_e5KXt09-Dk)**

---

## 📚 Créditos

Projeto acadêmico desenvolvido para a disciplina **Tópicos em Dispositivos Móveis – D2TDM**,   
ministrada pelo professor **Danilo (IFSP)**. 


## 🙋‍♂️

🔗 Conecte-se comigo

[LinkedIn - Márcio Adriano](https://www.linkedin.com/in/mads1974/)
