# 📌 Echoes of the Forgotten

---

## 🚀 Come eseguire il progetto

### Prerequisiti
- Java 21 (LTS)
- Gradle

### Istruzioni

```bash
git clone <https://github.com/alygorithm/echoesOfTheForgotten.git>
cd frieren_rpg
```

### Build del progetto
Aprire il terminale e digitare:
```bash
./gradlew build
```

### Run/Esecuzione del progetto
```bash
./gradlew run
```

---

## 🤖 Uso di strumenti di AI
Gli strumenti di AI (ChatGPT e Gemini) sono stati utilizzati principalmente
come supporto e assistenza alla programmazione durante lo sviluppo del progetto,
con un focus particolare sulla raffinazione del codice e sulla corenza dell'interfaccia utente.

### Come sono stati integrati:
* **ChatGPT (Supporto al debugging e revisione narrativa):**
  * **Risoluzione di errori:** utilizzato come "compilatore interattivo" per analizzare e comprendere rapidamente i messaggi d'errore o di runtime apparsi nel terminale, velocizzando così l'attività di debugging senza sostituirsi alla scrittura del codice.
  * **Adattamento della storia:** è stato impiegato per revisionare, correggere e formattare i testi della narrazione (una storia originariamente scritta durante gli anni delle scuole superiori, ripresa e riadattata per l'occasione per sposarsi con la struttura a bivi del gioco).
  * **Chiarimenti temporanei:** consultato per risolvere dubbi mirari o ricevere spiegazioni teoriche su specifici comportamenti dei vari componenti.
  
* **Gemini (Generazione degli scenari e adattamento della Grafica):**
  * **Asset grafici:** Utilizzato per la generazione dei vari scenari di background visibili all'interno dell'interfaccia (come la biblioteca, la foresta, ecc.), identificabili dalla filigrana/stellina della generazione IA in basso a destra sulle immagini.
  * Sulla base del layout da me strutturato, l'AI ha fornito suggerimenti mirati per l'ottimizzazione dei componenti tramite fogli di stile CSS (eliminazione degli sfondi bianchi nativi di JavaFX sulle `TextArea`, gestione dei margini su `VBox` e `BorderPane`).

### ⚠️ Livello di intervento personale e controllo del codice:
Si tiene a specificare che **l'AI non ha generato l'applicazione**. Il codice è stato interamente scritto, supervisionato e testato manualmente riga per riga. L'intervento umano è stato fondamentale e predominante per:
1. Progettare l'architettura e il disaccoppiamento tra la logica di flusso (`GameFlow`), i servizi di sistema (`WorldService`, `SaveService`) e le varie schermate (`GameView`, `GameCompletedView`, `LoadGameView`), garantendo il rispetto del principio di singola responsabilità (SRP).
2. Calibrare manualmente le dimensioni geometriche precise dei pannelli e delle font (es. la larghezza della barra delle statistiche fissata a 165 per avvolgere perfettamente il testo in ASCII art).
3. Implementare la logica reale dietro ai bivi e alla persistenza dei dati.

L'uso dell'IA è stato quindi limitato a compiti di rifinitura estetica, correzione di refusi e come supporto didattico per ottimizzare i tempi di sviluppo.
