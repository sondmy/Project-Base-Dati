CREATE TABLE IF NOT EXISTS Ruoli (
    id_ruolo    SERIAL PRIMARY KEY,
    nome        VARCHAR(100) NOT NULL,
    descrizione VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS Permessi (
    id_permesso    SERIAL PRIMARY KEY,
    codice         VARCHAR(100) NOT NULL UNIQUE,
    descrizione    VARCHAR(255),
    tabella_target VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS Ruoli_Permesso (
    id_ruolo    INT NOT NULL REFERENCES Ruoli(id_ruolo) ON DELETE CASCADE,
    id_permesso INT NOT NULL REFERENCES Permessi(id_permesso) ON DELETE CASCADE,
    PRIMARY KEY (id_ruolo, id_permesso)
);

CREATE TABLE IF NOT EXISTS Specie (
    nome_scientifico VARCHAR(150) PRIMARY KEY,
    stato_esistenza  VARCHAR(100),
    tipo_animale     VARCHAR(100),
    nome_comune      VARCHAR(150),
    habitat          VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS Aree (
    id_area   SERIAL PRIMARY KEY,
    nome      VARCHAR(150) NOT NULL,
    metratura INT,
    tipo      VARCHAR(100)
);


CREATE TABLE IF NOT EXISTS Recinti (
    id_recinto   SERIAL PRIMARY KEY,
    tipo_recinto VARCHAR(100),
    id_area      INT NOT NULL REFERENCES Aree(id_area) ON DELETE RESTRICT
);

CREATE TABLE IF NOT EXISTS Animali (
    id_animale       SERIAL PRIMARY KEY,
    nome             VARCHAR(150),
    vivo             BOOLEAN NOT NULL DEFAULT TRUE,
    data_nascita     DATE,
    data_arrivo      DATE,
    nome_scientifico VARCHAR(150) REFERENCES Specie(nome_scientifico) ON DELETE SET NULL,
    id_recinto       INT          REFERENCES Recinti(id_recinto) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Diete (
    id_dieta         SERIAL PRIMARY KEY,
    nome_scientifico VARCHAR(150) REFERENCES Specie(nome_scientifico) ON DELETE CASCADE,
    tipo_cibo        VARCHAR(150),
    quantita         DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS Dipendenti (
    codice_fiscale  VARCHAR(16)  PRIMARY KEY,
    nome            VARCHAR(100) NOT NULL,
    cognome         VARCHAR(100) NOT NULL,
    anno_nascita    DATE,
    ruolo           INT          REFERENCES Ruoli(id_ruolo) ON DELETE SET NULL,
    data_assunzione DATE,
    prezzo_orario   DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS Utenti (
    id_utente          SERIAL PRIMARY KEY,
    email              VARCHAR(255) NOT NULL UNIQUE,
    password           VARCHAR(255) NOT NULL,
    data_registrazione DATE         NOT NULL DEFAULT CURRENT_DATE,
    id_ruolo           INT          REFERENCES Ruoli(id_ruolo) ON DELETE SET NULL,
    codice_fiscale     VARCHAR(16)  REFERENCES Dipendenti(codice_fiscale) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Categorie (
    id_categoria      SERIAL PRIMARY KEY,
    nome              VARCHAR(150) NOT NULL,
    descrizione       VARCHAR(255),
    tipo_entrata_uscita BOOLEAN NOT NULL
);

CREATE TABLE IF NOT EXISTS Entrate (
    id_entrata       SERIAL PRIMARY KEY,
    categoria_entrata INT             REFERENCES Categorie(id_categoria) ON DELETE SET NULL,
    data_entrata     DATE             NOT NULL DEFAULT CURRENT_DATE,
    importo          DOUBLE PRECISION NOT NULL,
    descrizione      VARCHAR(255),
    id_utente        INT              REFERENCES Utenti(id_utente) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Uscite (
    id_uscite        SERIAL PRIMARY KEY,
    categoria_uscite INT             REFERENCES Categorie(id_categoria) ON DELETE SET NULL,
    data_uscite      DATE            NOT NULL DEFAULT CURRENT_DATE,
    importo          DOUBLE PRECISION NOT NULL,
    descrizione      VARCHAR(255),
    id_utente        INT             REFERENCES Utenti(id_utente) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Tipo_Biglietti (
    id_biglietto SERIAL PRIMARY KEY,
    nome         VARCHAR(150) NOT NULL,
    descrizione  VARCHAR(255),
    prezzo       DOUBLE PRECISION NOT NULL,
    attivo       BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS Scontrini (
    id_scontrino  SERIAL PRIMARY KEY,
    data_acquisto DATE             NOT NULL DEFAULT CURRENT_DATE,
    prezzo_totale DOUBLE PRECISION NOT NULL,
    nome_gruppo   VARCHAR(150),
    num_persone   INT
);

CREATE TABLE IF NOT EXISTS Dettaglio_scontrini (
    id_dettaglio              SERIAL PRIMARY KEY,
    id_biglietto              INT             REFERENCES Tipo_Biglietti(id_biglietto) ON DELETE RESTRICT,
    scontrino                 INT             REFERENCES Scontrini(id_scontrino) ON DELETE CASCADE,
    quantita                  INT             NOT NULL DEFAULT 1,
    prezzo_pagato_biglietto   DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS Visite_Mediche (
    id_trattamento   SERIAL PRIMARY KEY,
    peso             DOUBLE PRECISION,
    diagnosi         VARCHAR(255),
    note_trattamento TEXT,
    data_visita      DATE NOT NULL,
    data_fine        DATE,
    id_animale       INT         REFERENCES Animali(id_animale) ON DELETE SET NULL,
    id_veterinario   VARCHAR(16) REFERENCES Dipendenti(codice_fiscale) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Turni (
    id_lavoro      SERIAL PRIMARY KEY,
    ora_inizio     TIMESTAMP NOT NULL,
    ora_fine       TIMESTAMP NOT NULL,
    codice_fiscale VARCHAR(16) REFERENCES Dipendenti(codice_fiscale) ON DELETE SET NULL,
    posto          INT         REFERENCES Aree(id_area) ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS Spese (
    id_acquisto SERIAL PRIMARY KEY,
    id_utente   INT REFERENCES Utenti(id_utente) ON DELETE SET NULL
);
