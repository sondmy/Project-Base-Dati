-- ============================================================
--  ZOO GESTIONALE — Database Initialization Script
--  Generato dallo schema Zoo.xml (Luna ER tool)
-- ============================================================

-- ENUM tipo transazione
DO $$ BEGIN
    CREATE TYPE tipo_transazione AS ENUM ('ENTRATA', 'USCITA');
EXCEPTION WHEN duplicate_object THEN NULL;
END $$;


-- ============================================================
-- LOOKUP TABLES (nessuna dipendenza)
-- ============================================================

CREATE TABLE IF NOT EXISTS Stato_Esistenza (
    id_stato    SERIAL        PRIMARY KEY,
    nome        VARCHAR(50)   NOT NULL,
    descrizione VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS Habitat (
    id_habitat  SERIAL        PRIMARY KEY,
    nome        VARCHAR(50)   NOT NULL,
    descrizione VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS Famiglia_Specie (
    id_famiglia_specie SERIAL       PRIMARY KEY,
    nome               VARCHAR(50)  NOT NULL,
    descrizione        VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS Tipo_Area (
    id_tipo_area SERIAL       PRIMARY KEY,
    nome         VARCHAR(50)  NOT NULL,
    descrizione  VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS Tipo_Recinto (
    id_tipo_recinto SERIAL       PRIMARY KEY,
    nome            VARCHAR(50)  NOT NULL,
    descrizione     VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS Tipo_Cibo (
    id_tipo_cibo SERIAL       PRIMARY KEY,
    nome         VARCHAR(50)  NOT NULL,
    descrizione  VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS Tipo_Fornitura (
    id_tipo_fornitura SERIAL       PRIMARY KEY,
    nome              VARCHAR(50)  NOT NULL,
    descrizione       VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS Mansione (
    id_mansione SERIAL       PRIMARY KEY,
    nome        VARCHAR(50)  NOT NULL,
    descrizione VARCHAR(250)
);

CREATE TABLE IF NOT EXISTS Tipo_Biglietto (
    id_biglietto SERIAL           PRIMARY KEY,
    nome         VARCHAR(50)      NOT NULL,
    descrizione  VARCHAR(250),
    prezzo       DOUBLE PRECISION NOT NULL,
    attivo       BOOLEAN          NOT NULL DEFAULT TRUE
);


-- ============================================================
-- SPECIE
-- Dipende da: Stato_Esistenza, Habitat, Famiglia_Specie
-- ============================================================
CREATE TABLE IF NOT EXISTS Specie (
    id_specie          SERIAL      PRIMARY KEY,
    nome_scientifico   VARCHAR(50) NOT NULL,
    nome_comune        VARCHAR(50) NOT NULL,
    id_habitat         INT         REFERENCES Habitat(id_habitat)               ON DELETE SET NULL,
    id_stato           INT         REFERENCES Stato_Esistenza(id_stato)         ON DELETE SET NULL,
    id_famiglia_specie INT         REFERENCES Famiglia_Specie(id_famiglia_specie) ON DELETE SET NULL
);

-- ============================================================
-- DIETA (M:N tra Specie e Tipo_Cibo)
-- ============================================================
CREATE TABLE IF NOT EXISTS Dieta (
    id_specie          INT              NOT NULL REFERENCES Specie(id_specie)        ON DELETE CASCADE,
    id_tipo_cibo       INT              NOT NULL REFERENCES Tipo_Cibo(id_tipo_cibo)  ON DELETE RESTRICT,
    quantita_kg_giorno DOUBLE PRECISION NOT NULL,
    PRIMARY KEY (id_specie, id_tipo_cibo)
);


-- ============================================================
-- AREA
-- Dipende da: Tipo_Area
-- ============================================================
CREATE TABLE IF NOT EXISTS Area (
    id_area      SERIAL      PRIMARY KEY,
    nome         VARCHAR(50) NOT NULL,
    metratura    INT         NOT NULL,
    id_tipo_area INT         NOT NULL REFERENCES Tipo_Area(id_tipo_area) ON DELETE RESTRICT
);

-- ============================================================
-- RECINTO
-- Dipende da: Area, Tipo_Recinto
-- ============================================================
CREATE TABLE IF NOT EXISTS Recinto (
    id_recinto      SERIAL PRIMARY KEY,
    capienza_massima INT   NOT NULL,
    id_area         INT   NOT NULL REFERENCES Area(id_area)                 ON DELETE RESTRICT,
    id_tipo_recinto INT   NOT NULL REFERENCES Tipo_Recinto(id_tipo_recinto) ON DELETE RESTRICT
);

-- ============================================================
-- ANIMALE
-- Dipende da: Recinto, Specie
-- ============================================================
CREATE TABLE IF NOT EXISTS Animale (
    id_animale   SERIAL      PRIMARY KEY,
    nome         VARCHAR(50) NOT NULL,
    sesso        CHAR(1)     NOT NULL CHECK (sesso IN ('M', 'F')),
    attivo       BOOLEAN     NOT NULL DEFAULT TRUE,
    data_nascita DATE,
    data_arrivo  DATE,
    data_uscita  DATE,
    id_recinto   INT         NOT NULL REFERENCES Recinto(id_recinto) ON DELETE RESTRICT,
    id_specie    INT         NOT NULL REFERENCES Specie(id_specie)   ON DELETE RESTRICT
);

-- ============================================================
-- STORICO_COLLOCAZIONE (storico dei recinti dell'animale nel tempo)
-- Dipende da: Animale, Recinto
-- ============================================================
CREATE TABLE IF NOT EXISTS Storico_Collocazione (
    id_storico  SERIAL PRIMARY KEY,
    id_animale  INT    NOT NULL REFERENCES Animale(id_animale)   ON DELETE CASCADE,
    id_recinto  INT    NOT NULL REFERENCES Recinto(id_recinto)   ON DELETE RESTRICT,
    data_inizio DATE   NOT NULL,
    data_fine   DATE
);

-- ============================================================
-- MANSIONE / DIPENDENTE
-- ============================================================
CREATE TABLE IF NOT EXISTS Dipendente (
    id_dipendente  SERIAL           PRIMARY KEY,
    codice_fiscale VARCHAR(50)      NOT NULL UNIQUE,
    nome           VARCHAR(50)      NOT NULL,
    cognome        VARCHAR(50)      NOT NULL,
    data_nascita   DATE,
    data_assunzione DATE            NOT NULL,
    prezzo_orario  DOUBLE PRECISION NOT NULL,
    id_mansione    INT              NOT NULL REFERENCES Mansione(id_mansione) ON DELETE RESTRICT
);

-- ============================================================
-- STORICO_STIPENDIO
-- Dipende da: Dipendente
-- ============================================================
CREATE TABLE IF NOT EXISTS Storico_Stipendio (
    id_storico    SERIAL           PRIMARY KEY,
    id_dipendente INT              NOT NULL REFERENCES Dipendente(id_dipendente) ON DELETE CASCADE,
    prezzo_orario DOUBLE PRECISION NOT NULL,
    data_inizio   DATE             NOT NULL,
    data_fine     DATE
);

-- ============================================================
-- TURNO
-- Dipende da: Dipendente, Area
-- ============================================================
CREATE TABLE IF NOT EXISTS Turno (
    id_turno      SERIAL    PRIMARY KEY,
    ora_inizio    TIMESTAMP NOT NULL,
    ora_fine      TIMESTAMP NOT NULL,
    id_dipendente INT       NOT NULL REFERENCES Dipendente(id_dipendente) ON DELETE RESTRICT,
    id_area       INT       NOT NULL REFERENCES Area(id_area)             ON DELETE RESTRICT
);

-- ============================================================
-- VISITA_MEDICA
-- Dipende da: Animale, Dipendente
-- ============================================================
CREATE TABLE IF NOT EXISTS Visita_Medica (
    id_visita        SERIAL           PRIMARY KEY,
    peso             DOUBLE PRECISION,
    diagnosi         VARCHAR(50),
    note_trattamento VARCHAR(250),
    data_visita      DATE             NOT NULL,
    data_fine        DATE,
    id_animale       INT              NOT NULL REFERENCES Animale(id_animale)      ON DELETE RESTRICT,
    id_dipendente    INT              NOT NULL REFERENCES Dipendente(id_dipendente) ON DELETE RESTRICT
);

-- ============================================================
-- UTENTE
-- Dipende da: Dipendente
-- ============================================================
CREATE TABLE IF NOT EXISTS Utente (
    id_utente          SERIAL       PRIMARY KEY,
    email              VARCHAR(50)  NOT NULL UNIQUE,
    password_hash      VARCHAR(50)  NOT NULL,
    data_registrazione DATE         NOT NULL DEFAULT CURRENT_DATE,
    ruolo              VARCHAR(20)  NOT NULL,
    id_dipendente      INT          REFERENCES Dipendente(id_dipendente) ON DELETE SET NULL
    -- nullable: non tutti gli utenti sono dipendenti
);

-- ============================================================
-- FORNITORE
-- Dipende da: Tipo_Fornitura
-- ============================================================
CREATE TABLE IF NOT EXISTS Fornitore (
    id_fornitore      SERIAL       PRIMARY KEY,
    nome_azienda      VARCHAR(50)  NOT NULL,
    descrizione       VARCHAR(250),
    indirizzo         VARCHAR(50),
    iban              VARCHAR(50),
    id_tipo_fornitura INT          REFERENCES Tipo_Fornitura(id_tipo_fornitura) ON DELETE SET NULL
);

-- ============================================================
-- FORNITORE_CIBO (M:N tra Fornitore e Tipo_Cibo)
-- ============================================================
CREATE TABLE IF NOT EXISTS Fornitore_Cibo (
    id_fornitore INT NOT NULL REFERENCES Fornitore(id_fornitore)       ON DELETE CASCADE,
    id_tipo_cibo INT NOT NULL REFERENCES Tipo_Cibo(id_tipo_cibo)       ON DELETE RESTRICT,
    PRIMARY KEY (id_fornitore, id_tipo_cibo)
);

-- ============================================================
-- SCONTRINO
-- Dipende da: Utente
-- ============================================================
CREATE TABLE IF NOT EXISTS Scontrino (
    id_scontrino  SERIAL       PRIMARY KEY,
    data_acquisto DATE         NOT NULL DEFAULT CURRENT_DATE,
    nome_gruppo   VARCHAR(50),
    num_persone   INT,
    id_utente     INT          NOT NULL REFERENCES Utente(id_utente) ON DELETE RESTRICT
);

-- ============================================================
-- DETTAGLIO_SCONTRINO
-- Dipende da: Scontrino, Tipo_Biglietto
-- ============================================================
CREATE TABLE IF NOT EXISTS Dettaglio_Scontrino (
    id_dettaglio            SERIAL           PRIMARY KEY,
    id_scontrino            INT              NOT NULL REFERENCES Scontrino(id_scontrino)        ON DELETE CASCADE,
    id_biglietto            INT              NOT NULL REFERENCES Tipo_Biglietto(id_biglietto)   ON DELETE RESTRICT,
    quantita                INT              NOT NULL DEFAULT 1,
    prezzo_pagato_biglietto DOUBLE PRECISION NOT NULL
);

-- ============================================================
-- CATEGORIA_TRANSAZIONE
-- ============================================================
CREATE TABLE IF NOT EXISTS Categoria_Transazione (
    id_categoria SERIAL           PRIMARY KEY,
    nome         VARCHAR(50)      NOT NULL,
    descrizione  VARCHAR(250),
    tipo         tipo_transazione NOT NULL  -- 'ENTRATA' | 'USCITA'
);

-- ============================================================
-- TRANSAZIONE
-- Dipende da: Categoria_Transazione, Utente, Fornitore, Scontrino
-- ============================================================
CREATE TABLE IF NOT EXISTS Transazione (
    id_transazione SERIAL            PRIMARY KEY,
    tipo           tipo_transazione  NOT NULL,
    importo        DOUBLE PRECISION  NOT NULL CHECK (importo > 0),
    data           DATE              NOT NULL DEFAULT CURRENT_DATE,
    descrizione    VARCHAR(250),
    id_categoria   INT               NOT NULL REFERENCES Categoria_Transazione(id_categoria) ON DELETE RESTRICT,
    id_utente      INT               NOT NULL REFERENCES Utente(id_utente)                   ON DELETE RESTRICT,
    id_fornitore   INT                        REFERENCES Fornitore(id_fornitore)             ON DELETE SET NULL,
    id_scontrino   INT                        REFERENCES Scontrino(id_scontrino)             ON DELETE SET NULL
);

-- ============================================================
-- ORDINE_GIORNALIERO_CIBO
-- Dipende da: Fornitore, Tipo_Cibo, Transazione
-- ============================================================
CREATE TABLE IF NOT EXISTS Ordine_Giornaliero_Cibo (
    id_ordine      SERIAL           PRIMARY KEY,
    data_ordine    DATE             NOT NULL,
    quantita_kg    DOUBLE PRECISION NOT NULL,
    id_fornitore   INT              NOT NULL REFERENCES Fornitore(id_fornitore)       ON DELETE RESTRICT,
    id_tipo_cibo   INT              NOT NULL REFERENCES Tipo_Cibo(id_tipo_cibo)       ON DELETE RESTRICT,
    id_transazione INT                       REFERENCES Transazione(id_transazione)   ON DELETE SET NULL
);

-- ============================================================
-- Fine script
-- ============================================================
