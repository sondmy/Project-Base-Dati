In caso si vuole vedere il contenuto del file ma in maniera piu "leggibile" 
è presente anche un file README.md.
inoltre questo è il link del github del progetto:
https://github.com/sondmy/Project-Base-Dati

Le password per fare i login dei 4 utenti è per tutti: password123
è anche possibile trovarla nella relazione

Per far partire il progetto senza .jar bisogna eseguire nella root del
progetto il comando (windows): ./gradlew run

Se si vuole avviare il progetto dal file .jar bisogna prima eseguire 
nella root del progetto il comando (Windows) : ./gradlew shadowJar
Successivamente per avviare il file .jar bisogna eseguire il comando 
nella root del progetto (Windows): java -jar .\build\libs\zoo-all.jar 

Project-Base-Dati

REQUISITI

Prima di avviare il progetto assicurati di avere installato:

Docker Desktop (Windows/macOS) oppure Docker Engine + Docker Compose (Linux)
Un terminale (PowerShell, CMD, Bash, Zsh, ecc.)
Un browser web per accedere a pgAdmin

AVVIO RAPIDO

Per utilizzare il progetto sono necessari solamente i primi 4 passaggi descritti in questa guida:

Verifica del file .env
Verifica del file docker-compose.yml
Verifica dei file SQL (init.sql e seed.sql)
Avvio dei container con: docker compose up -d

I passaggi successivi (accesso a pgAdmin e configurazione della connessione) sono facoltativi e servono esclusivamente per amministrare e visualizzare il database tramite interfaccia grafica.

PASSO 1 — VERIFICA FILE .ENV

Verifica che nella cartella postgres-docker/ sia presente il file .env con questo contenuto:

COMPOSE_PROJECT_NAME=zoo_gestionale

POSTGRES_HOST=localhost
POSTGRES_DB=zoo_gestionale
POSTGRES_USER=zoo_user
POSTGRES_PASSWORD=zoo_password_sicura
POSTGRES_PORT=5432

PGADMIN_PORT=8080
PGADMIN_EMAIL=admin@local.dev
PGADMIN_PASSWORD=admin

PASSO 2 — VERIFICA IL FILE DOCKER-COMPOSE.YML

Verifica che nella cartella postgres-docker/ sia presente il file docker-compose.yml con il seguente contenuto:

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

PASSO 3 — AGGIUNGI I FILE SQL

Verifica che nella cartella ./postgres-docker/sql/ siano presenti i file init.sql e seed.sql se è la prima volta che viene avviato il programma.

PASSO 4 — AVVIA I CONTAINER

Apri il terminale nella root del progetto poi spostati dentro la cartella ./postgres-docker ed esegui:

docker compose up -d

-d avvia i container in background (detached mode)
Docker scaricherà le immagini al primo avvio (ci vuole qualche minuto)
Postgres eseguirà init.sql automaticamente e creerà tutte le tabelle

Per verificare che tutto stia girando correttamente:

docker compose ps

Dovresti vedere entrambi i container con stato healthy (per postgres) e running (per pgadmin).

Per vedere i log in tempo reale:

docker compose logs -f postgres

PASSO 5 — ACCEDI A PGADMIN

Apri il browser e vai su:

http://localhost:8080

Effettua il login con le credenziali del tuo .env:

Email: admin@local.dev
Password: admin

PASSO 6 — CONNETTI PGADMIN AL TUO DATABASE POSTGRES

Nel pannello sinistro, clicca con il tasto destro su "Servers" → "Register" → "Server..."
Nella scheda "General":
Name: Zoo Gestionale (o come preferisci)
Nella scheda "Connection":
Host name/address: zoo_gestionale_postgres
NON usare localhost (dentro Docker si usa il nome del container)
Port: 5432
Maintenance database: zoo_gestionale
Username: zoo_user
Password: zoo_password_sicura
Spunta "Save password"
Clicca "Save"

Percorso tabelle:

Servers → Zoo Gestionale → Databases → zoo_gestionale → Schemas → public → Tables

COMANDI UTILI

docker compose up -d Avvia tutti i container in background
docker compose down Ferma e rimuove i container (i dati restano nei volumi)
docker compose down -v Ferma i container e cancella tutti i dati (attenzione!)
docker compose logs -f postgres Segue i log di Postgres in tempo reale
docker compose restart postgres Riavvia solo Postgres
docker compose ps Mostra lo stato dei container

NOTE IMPORTANTI

Il file init.sql e seed.sql vengono eseguiti solamente al primo avvio
Se il database esiste già, per reinizializzare: docker compose down -v e riavvio
Le password nel .env sono solo per sviluppo locale
pgAdmin è per uso locale e non va esposto su internet senza sicurezza