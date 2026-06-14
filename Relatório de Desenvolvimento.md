# 📑 Relatório de Desenvolvimento: Projeto NutrIA

Este documento apresenta uma análise detalhada do ciclo de desenvolvimento do projeto **NutrIA**, compreendendo a distribuição de esforço pelas fases de engenharia, a dinâmica de cocriação entre inteligência artificial e o desenvolvedor, bem como a avaliação da experiência técnica adquirida.

---

## ⏳ 1. Gestão de Tempo: Fases e Esforço

O ciclo de desenvolvimento do projeto foi estruturado em quatro etapas principais, acumulando um tempo total estimado entre **6 a 8 horas** de trabalho focado e intensivo.

```
Fase 1: Configuração & Ambiente  [1.5h]  ██████
Fase 2: Integração com Gemini AI [1.5h]  ██████
Fase 3: Persistência com Room   [2.5h]  ██████████
Fase 4: Refinamento de UI/UX     [2.5h]  ██████████
```

### ⚙️ Detalhamento das Etapas:

* **Fase 1: Configuração e Resolução de Conflitos (1.5h)**
    * **Foco:** Infraestrutura e build do ecossistema Android.
    * **Desafios:** Ajuste do `compileSdk` para a **versão 36** para garantir total compatibilidade com bibliotecas modernas e configuração do **KSP (Kotlin Symbol Processing)**, essencial para a correta geração de código do Room Database.
* **Fase 2: Integração com Inteligência Artificial (1.5h)**
    * **Foco:** Comunicação e estabilização da camada de IA.
    * **Desafios:** Engenharia de Prompt (*Prompt Engineering*) para estruturar as respostas da IA e tratamento de exceções de rede (como os erros HTTP 404 e 503), assegurando o mapeamento correto do modelo estável `gemini-3.1-flash-lite`.
* **Fase 3: Persistência de Dados com Room (2.5h)**
    * **Foco:** Criação do modelo relacional local.
    * **Desafios:** Implementação detalhada de entidades, DAOs e regras de persistência. O esforço concentrou-se em garantir o armazenamento síncrono e imediato da análise gerada pela IA assim que o retorno HTTP fosse concluído com sucesso.
* **Fase 4: Refinamento de UI e UX (2.5h)**
    * **Foco:** Experiência do utilizador e fluxos de navegação.
    * **Desafios:** Construção do painel principal (*Dashboard*), formatação reativa de carimbos de data/hora e implementação do mecanismo de exclusão rápida (*Limpar Histórico*) para fins de validação e gravação de demonstrações em vídeo.

---

## 🤖 2. Autoria do Código: Humano vs. IA

O modelo de trabalho adotado seguiu o paradigma de **Cocriação Assistida**, onde a Inteligência Artificial atuou como um copiloto técnico especializado sob a liderança e supervisão da arquitetura humana.

```
🤖 Código Gerado por IA [80%]  ████████████████████████████████
👤 Direcionamento Humano [20%]  ████████
```

| Ator | Contribuição Estimada | Atividades Principais |
| :--- | :---: | :--- |
| **Inteligência Artificial** | **~80%** | Geração de código estrutural (*boilerplate*), classes de configuração do Room, esqueletos de ecrãs em Jetpack Compose, funções de consumo do SDK do Gemini e sugestões automáticas de resolução de dependências no `build.gradle.kts`. |
| **Engenheiro Humano** | **~20%** | Definição da arquitetura de dados, modelagem de requisitos nutricionais, design de interação (fluxo Dashboard vs. Registo), governança da marca (definição do nome *NutrIA*) e validação de domínio para garantir a precisão dos dados para cenários reais de nutrição. |

> 💡 **Síntese:** A IA otimizou drasticamente a velocidade de escrita do código bruto, enquanto a supervisão humana garantiu a integridade, a coerência arquitetural e a integração correta entre os módulos do sistema.

---

## 🧠 3. Sentimentos e Experiência do Desenvolvedor

O percurso de engenharia simulou uma curva de aprendizagem ágil, intercalando momentos de alta produtividade com desafios técnicos de resolução rápida.

* **🤩 Animação:**
    * *O ponto alto do projeto:* O momento em que a primeira captura de imagem foi submetida, processada com sucesso pela API do Gemini e listada de forma reativa e automática no histórico local do painel principal. A visualização prática do fluxo funcional gerou alto nível de motivação.
* **😤 Frustração:**
    * *Os pontos de atrito:* Instabilidades causadas por conflitos de versões de bibliotecas (SDK 35 vs 36) e tratamento dos códigos de erro 404/503 da API. A rápida evolução tecnológica dos modelos de linguagem exige resiliência para pesquisar documentações e adaptar código depreciado.
* **💪 Confiança:**
    * *A perceção final:* Consolidação do domínio sobre ferramentas modernas de desenvolvimento. A conclusão com sucesso de uma aplicação robusta que unifica inteligência artificial generativa na cloud com armazenamento local persistente valida a capacidade técnica para projetos futuros de maior escala.
* **😮‍💨 Alívio:**
    * *A recompensa técnica:* O sucesso na execução do comando *Clean e Rebuild* após a resolução de erros persistentes de compilação, restaurando a estabilidade da árvore de execução do projeto.

---

## 🏁 Conclusão

A construção do **NutrIA** comprovou a eficiência da engenharia de software assistida por inteligência artificial. O uso estratégico de ferramentas baseadas em modelos generativos permitiu mitigar o tempo despendido em sintaxes repetitivas e configurações padrão, deslocando o foco principal do desenvolvedor para o valor real do produto: a **experiência do utilizador** e a **regra de negócio**. O resultado é uma aplicação estável, escalável e pronta para distribuição.
