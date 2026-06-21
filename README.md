# Project-Base-Dati
Creazione di un Gestionale di uno zoo come progetto per sostenere l'esame di base di dati..


# Docker

## Passo 1 — Verifica file .env

Verifica che nella cartella `postgres-docker/` sia presente il file `.env` con questo contenuto:

```env
COMPOSE_PROJECT_NAME=zoo_gestionale

POSTGRES_HOST=localhost
POSTGRES_DB=zoo_gestionale
POSTGRES_USER=zoo_user
POSTGRES_PASSWORD=zoo_password_sicura
POSTGRES_PORT=5432

PGADMIN_PORT=8080
PGADMIN_EMAIL=admin@local.dev
PGADMIN_PASSWORD=admin
```


## Passo 2 — Crea il file `docker-compose.yml`

Verifica che nella cartella `postgres-docker/` sia presente il file `docker-compose.yml` con il seguente contenuto:

```yaml
services:
  postgres:
    image: postgres:17
    container_name: ${COMPOSE_PROJECT_NAME}_postgres
    restart: unless-stopped
    environment:
      POSTGRES_DB: ${POSTGRES_DB}
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
      PGDATA: /var/lib/postgresql/data/pgdata
    ports:
      - "${POSTGRES_PORT:-5432}:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data
      - ./sql:/docker-entrypoint-initdb.d
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U ${POSTGRES_USER} -d ${POSTGRES_DB}"]
      interval: 10s
      timeout: 5s
      retries: 5
      start_period: 30s

  pgadmin:
    image: dpage/pgadmin4:latest
    container_name: ${COMPOSE_PROJECT_NAME}_pgadmin
    restart: unless-stopped
    environment:
      PGADMIN_DEFAULT_EMAIL: ${PGADMIN_EMAIL:-admin@local.dev}
      PGADMIN_DEFAULT_PASSWORD: ${PGADMIN_PASSWORD:-admin}
      PGADMIN_CONFIG_SERVER_MODE: "False"
      PGADMIN_CONFIG_MASTER_PASSWORD_REQUIRED: "False"
    ports:
      - "${PGADMIN_PORT:-8080}:80"
    depends_on:
      postgres:
        condition: service_healthy
    volumes:
      - pgadmin_data:/var/lib/pgadmin

volumes:
  pgdata:
  pgadmin_data:
```

## Passo 3 — Aggiungi i file SQL

Verifica che nella cartella `./postgres-docker/sql/` siano presenti i file `init.sql` e `seed.sql` se è la prima volta che viene avviato il programma.

## Passo 4 — Avvia i container

Apri il terminale nella root del progetto ed esegui:

```bash
docker compose up -d
```

- `-d` avvia i container in background (detached mode)
- Docker scaricherà le immagini al primo avvio (ci vuole qualche minuto)
- Postgres eseguirà `init.sql` automaticamente e creerà tutte le tabelle

Per verificare che tutto stia girando correttamente:

```bash
docker compose ps
```

Dovresti vedere entrambi i container con stato `healthy` (per postgres) e `running` (per pgadmin).

Per vedere i log in tempo reale:

```bash
docker compose logs -f postgres
```

---

## Passo 5 — Accedi a pgAdmin

Apri il browser e vai su:

```
http://localhost:8080
```

Effettua il login con le credenziali del tuo `.env`:
- **Email:** `admin@local.dev`
- **Password:** `admin`

---

## Passo 6 — Connetti pgAdmin al tuo database Postgres

Una volta dentro pgAdmin, devi aggiungere la connessione al server Postgres:

1. Nel pannello sinistro, clicca con il tasto destro su **"Servers"** → **"Register"** → **"Server..."**

2. Nella scheda **"General"**:
   - **Name:** `Zoo Gestionale` (o come preferisci)

3. Nella scheda **"Connection"**:
   - **Host name/address:** `zoo_gestionale_postgres`
     > ⚠️ Non usare `localhost`! Dentro la rete Docker, pgAdmin raggiunge Postgres tramite il nome del container.
   - **Port:** `5432`
   - **Maintenance database:** `zoo_gestionale`
   - **Username:** `zoo_user`
   - **Password:** `zoo_password_sicura`
   - Spunta **"Save password"** per non reinserirla ogni volta

4. Clicca **"Save"**

Ora nel pannello sinistro vedrai il server connesso. Naviga fino a:

```
Servers → Zoo Gestionale → Databases → zoo_gestionale → Schemas → public → Tables
```

Troverai tutte le tabelle create dallo script SQL.

---

## Comandi utili

| Comando | Cosa fa |
|---|---|
| `docker compose up -d` | Avvia tutti i container in background |
| `docker compose down` | Ferma e rimuove i container (i dati restano nei volumi) |
| `docker compose down -v` | Ferma i container **e cancella** tutti i dati (attenzione!) |
| `docker compose logs -f postgres` | Segue i log di Postgres in tempo reale |
| `docker compose restart postgres` | Riavvia solo il container Postgres |
| `docker compose ps` | Mostra lo stato dei container |

---

## ⚠️ Note importanti

- **Il file `init.sql` viene eseguito solo al primo avvio**, quando il volume `pgdata` è vuoto. Se il database esiste già, Postgres ignora la cartella `initdb.d`. Per forzare una reinizializzazione devi cancellare il volume con `docker compose down -v` e riavviare.
- **Le password nel `.env`** sono solo per sviluppo locale. In produzione usa segreti gestiti (Docker Secrets, Vault, ecc.).
- **pgAdmin** è uno strumento solo per sviluppo/amministrazione locale. Non esporlo mai su internet senza autenticazione sicura.
