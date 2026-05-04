-- ============================================================
--  ZOO DATABASE SCHEMA
--  PostgreSQL
-- ============================================================

-- Estensione necessaria per i constraint EXCLUDE (no-overlap)
CREATE EXTENSION IF NOT EXISTS btree_gist;


-- ============================================================
--  TABELLE DI LOOKUP
-- ============================================================

CREATE TABLE STATO_ESISTENZA (
    id_stato    INTEGER         NOT NULL,
    nome        VARCHAR(50)     NOT NULL,
    descrizione VARCHAR(250),
    CONSTRAINT PK_STATO_ESISTENZA PRIMARY KEY (id_stato)
);
COMMENT ON TABLE  STATO_ESISTENZA          IS 'Stato di conservazione della specie (es. estinto, vulnerabile, in pericolo)';
COMMENT ON COLUMN STATO_ESISTENZA.nome     IS 'Nome dello stato (es. LC, VU, EN, CR, EW, EX)';


CREATE TABLE HABITAT (
    id_habitat  INTEGER         NOT NULL,
    nome        VARCHAR(50)     NOT NULL,
    descrizione VARCHAR(250),
    CONSTRAINT PK_HABITAT PRIMARY KEY (id_habitat)
);
COMMENT ON TABLE HABITAT IS 'Habitat naturale di una specie (es. foresta tropicale, savana, artico)';


CREATE TABLE FAMIGLIA_SPECIE (
    id_famiglia_specie  INTEGER     NOT NULL,
    nome                VARCHAR(50) NOT NULL,
    descrizione         VARCHAR(250),
    CONSTRAINT PK_FAMIGLIA_SPECIE PRIMARY KEY (id_famiglia_specie)
);
COMMENT ON TABLE FAMIGLIA_SPECIE IS 'Famiglia tassonomica della specie (es. Felidae, Canidae)';




CREATE TABLE TIPO_AREA (
    id_tipo_area    INTEGER     NOT NULL,
    nome            VARCHAR(50) NOT NULL,
    descrizione     VARCHAR(250),
    CONSTRAINT PK_TIPO_AREA PRIMARY KEY (id_tipo_area)
);
COMMENT ON TABLE TIPO_AREA IS 'Tipologia di area dello zoo (es. zoo, veterinaria, amministrativa)';


CREATE TABLE TIPO_RECINTO (
    id_tipo_recinto INTEGER     NOT NULL,
    nome            VARCHAR(50) NOT NULL,
    descrizione     VARCHAR(250),
    CONSTRAINT PK_TIPO_RECINTO PRIMARY KEY (id_tipo_recinto)
);
COMMENT ON TABLE TIPO_RECINTO IS 'Tipologia strutturale del recinto (es. gabbia, vasca, voliera)';


CREATE TABLE TIPO_MANSIONE (
    id_tipo_mansione    INTEGER     NOT NULL,
    nome                VARCHAR(50) NOT NULL,
    descrizione         VARCHAR(250),
    CONSTRAINT PK_TIPO_MANSIONE PRIMARY KEY (id_tipo_mansione)
);
COMMENT ON TABLE TIPO_MANSIONE IS 'Ruolo lavorativo del dipendente (es. veterinario, guardiano, amministratore)';


CREATE TABLE TIPO_CIBO (
    id_tipo_cibo    INTEGER     NOT NULL,
    nome            VARCHAR(50) NOT NULL,
    descrizione     VARCHAR(250),
    CONSTRAINT PK_TIPO_CIBO PRIMARY KEY (id_tipo_cibo)
);
COMMENT ON TABLE TIPO_CIBO IS 'Categoria alimentare usata nelle diete (es. carne, pesce, vegetali)';


CREATE TABLE TIPO_FORNITURA (
    id_tipo_fornitura   INTEGER     NOT NULL,
    nome                VARCHAR(50) NOT NULL,
    descrizione         VARCHAR(250),
    CONSTRAINT PK_TIPO_FORNITURA PRIMARY KEY (id_tipo_fornitura)
);
COMMENT ON TABLE TIPO_FORNITURA IS 'Categoria merceologica del fornitore (es. alimentari, farmaceutici, manutenzione)';


CREATE TABLE TIPO_BIGLIETTI (
    id_biglietto    INTEGER         NOT NULL,
    nome            VARCHAR(50)     NOT NULL,
    descrizione     VARCHAR(250),
    prezzo          NUMERIC(10,2)   NOT NULL,
    attivo          BOOLEAN         NOT NULL DEFAULT TRUE,
    CONSTRAINT PK_TIPO_BIGLIETTI    PRIMARY KEY (id_biglietto),
    CONSTRAINT chk_biglietto_prezzo CHECK (prezzo >= 0)
);
COMMENT ON COLUMN TIPO_BIGLIETTI.attivo IS 'TRUE = biglietto in vendita, FALSE = ritirato dal catalogo';


CREATE TABLE CATEGORIE_TRANSAZIONI (
    id_categoria    INTEGER     NOT NULL,
    nome            VARCHAR(50) NOT NULL,
    descrizione     VARCHAR(250),
    tipo            CHAR(1)     NOT NULL,
    CONSTRAINT PK_CATEGORIE_TRANSAZIONI PRIMARY KEY (id_categoria),
    CONSTRAINT chk_categoria_tipo       CHECK (tipo IN ('E', 'U'))
);
COMMENT ON COLUMN CATEGORIE_TRANSAZIONI.tipo IS 'E = entrata (incasso), U = uscita (spesa)';


-- ============================================================
--  SPECIE E DIETA
-- ============================================================

CREATE TABLE SPECIE (
    id_specie           INTEGER     NOT NULL,
    nome_scientifico    VARCHAR(50) NOT NULL,
    nome_comune         VARCHAR(50) NOT NULL,
    id_stato            INTEGER     NOT NULL,
    id_habitat          INTEGER     NOT NULL,
    id_famiglia_specie  INTEGER     NOT NULL,
    CONSTRAINT PK_SPECIE            PRIMARY KEY (id_specie),
    CONSTRAINT FKconservazione      FOREIGN KEY (id_stato)           REFERENCES STATO_ESISTENZA(id_stato),
    CONSTRAINT FKprovenienza        FOREIGN KEY (id_habitat)         REFERENCES HABITAT(id_habitat),
    CONSTRAINT FKcategoria          FOREIGN KEY (id_famiglia_specie) REFERENCES FAMIGLIA_SPECIE(id_famiglia_specie),
);


CREATE TABLE DIETA (
    id_specie           INTEGER         NOT NULL,
    id_tipo_cibo        INTEGER         NOT NULL,
    quantita_kg_giorno  NUMERIC(10,2)   NOT NULL,
    CONSTRAINT PK_DIETA             PRIMARY KEY (id_specie, id_tipo_cibo),
    CONSTRAINT FKdieta_specie       FOREIGN KEY (id_specie)     REFERENCES SPECIE(id_specie),
    CONSTRAINT FKdieta_tipocibo     FOREIGN KEY (id_tipo_cibo)  REFERENCES TIPO_CIBO(id_tipo_cibo),
    CONSTRAINT chk_dieta_quantita   CHECK (quantita_kg_giorno > 0)
);


-- ============================================================
--  AREE E RECINTI
-- ============================================================

CREATE TABLE AREA (
    id_area         INTEGER         NOT NULL,
    nome            VARCHAR(50)     NOT NULL,
    metratura       NUMERIC(10)     NOT NULL,
    id_tipo_area    INTEGER         NOT NULL,
    CONSTRAINT PK_AREA              PRIMARY KEY (id_area),
    CONSTRAINT FKcaratterizzazione  FOREIGN KEY (id_tipo_area) REFERENCES TIPO_AREA(id_tipo_area),
    CONSTRAINT chk_area_metratura   CHECK (metratura > 0)
);


CREATE TABLE RECINTO (
    id_recinto          INTEGER     NOT NULL,
    capienza_massima    NUMERIC(10) NOT NULL,
    id_tipo_recinto     INTEGER     NOT NULL,
    id_area             INTEGER     NOT NULL,
    CONSTRAINT PK_RECINTO           PRIMARY KEY (id_recinto),
    CONSTRAINT FKtipologia          FOREIGN KEY (id_tipo_recinto)   REFERENCES TIPO_RECINTO(id_tipo_recinto),
    CONSTRAINT FKubicazione         FOREIGN KEY (id_area)           REFERENCES AREA(id_area),
    CONSTRAINT chk_capienza         CHECK (capienza_massima > 0)
);


-- ============================================================
--  ANIMALI E STORICO COLLOCAZIONE
-- ============================================================

CREATE TABLE ANIMALE (
    id_animale      INTEGER     NOT NULL,
    nome            VARCHAR(50) NOT NULL,
    vivo            BOOLEAN     NOT NULL DEFAULT TRUE,
    data_nascita    DATE,
    data_arrivo     DATE,
    data_uscita     DATE,
    id_specie       INTEGER     NOT NULL,
    CONSTRAINT PK_ANIMALE           PRIMARY KEY (id_animale),
    CONSTRAINT FKappartenenza       FOREIGN KEY (id_specie) REFERENCES SPECIE(id_specie),
    CONSTRAINT chk_date_animale     CHECK (
        (data_arrivo IS NULL OR data_nascita IS NULL OR data_arrivo >= data_nascita)
        AND
        (data_uscita IS NULL OR data_arrivo IS NULL OR data_uscita >= data_arrivo)
    )
);
COMMENT ON COLUMN ANIMALE.vivo      IS 'TRUE = animale presente e vivo, FALSE = deceduto o uscito dallo zoo';
COMMENT ON COLUMN ANIMALE.data_uscita IS 'Data di uscita dallo zoo (cessione, decesso, trasferimento)';


CREATE TABLE STORICO_COLLOCAZIONE (
    id_storico      INTEGER NOT NULL,
    id_animale      INTEGER NOT NULL,
    id_recinto      INTEGER NOT NULL,
    data_inizio     DATE    NOT NULL,
    data_fine       DATE,
    CONSTRAINT PK_STORICO_COLLOCAZIONE      PRIMARY KEY (id_storico),
    CONSTRAINT FKcoll_animale               FOREIGN KEY (id_animale)    REFERENCES ANIMALE(id_animale),
    CONSTRAINT FKcoll_recinto               FOREIGN KEY (id_recinto)    REFERENCES RECINTO(id_recinto),
    CONSTRAINT chk_coll_date                CHECK (data_fine IS NULL OR data_fine >= data_inizio),
    -- Un animale non può essere in due recinti contemporaneamente
    CONSTRAINT no_overlap_collocazione      EXCLUDE USING gist (
        id_animale WITH =,
        daterange(data_inizio, data_fine) WITH &&
    )
);
COMMENT ON TABLE  STORICO_COLLOCAZIONE IS 'Storico delle collocazioni di ogni animale nei recinti nel tempo';
COMMENT ON COLUMN STORICO_COLLOCAZIONE.data_fine IS 'NULL = collocazione attualmente attiva';


-- ============================================================
--  DIPENDENTI E STIPENDI
-- ============================================================

CREATE TABLE DIPENDENTE (
    id_dipendente       INTEGER     NOT NULL,
    codice_fiscale      VARCHAR(16) NOT NULL,
    nome                VARCHAR(50) NOT NULL,
    cognome             VARCHAR(50) NOT NULL,
    compleanno          DATE,
    data_assunzione     DATE        NOT NULL,
    id_tipo_mansione    INTEGER     NOT NULL,
    CONSTRAINT PK_DIPENDENTE        PRIMARY KEY (id_dipendente),
    CONSTRAINT UQ_DIPENDENTE_CF     UNIQUE (codice_fiscale),
    CONSTRAINT FKruolo              FOREIGN KEY (id_tipo_mansione) REFERENCES TIPO_MANSIONE(id_tipo_mansione),
    CONSTRAINT chk_cf_length        CHECK (LENGTH(codice_fiscale) = 16)
);
COMMENT ON COLUMN DIPENDENTE.codice_fiscale IS 'Codice fiscale italiano, sempre 16 caratteri';


CREATE TABLE STORICO_STIPENDIO (
    id_storico      INTEGER         NOT NULL,
    id_dipendente   INTEGER         NOT NULL,
    prezzo_orario   NUMERIC(10,2)   NOT NULL,
    data_inizio     DATE            NOT NULL,
    data_fine       DATE,
    CONSTRAINT PK_STORICO_STIPENDIO     PRIMARY KEY (id_storico),
    CONSTRAINT FKretribuzione           FOREIGN KEY (id_dipendente) REFERENCES DIPENDENTE(id_dipendente),
    CONSTRAINT chk_stipendio_prezzo     CHECK (prezzo_orario > 0),
    CONSTRAINT chk_stipendio_date       CHECK (data_fine IS NULL OR data_fine >= data_inizio),
    -- Un dipendente non può avere due stipendi attivi nello stesso periodo
    CONSTRAINT no_overlap_stipendio     EXCLUDE USING gist (
        id_dipendente WITH =,
        daterange(data_inizio, data_fine) WITH &&
    )
);
COMMENT ON COLUMN STORICO_STIPENDIO.data_fine IS 'NULL = tariffa oraria attualmente in vigore';


-- ============================================================
--  TURNI
-- ============================================================

CREATE TABLE TURNO (
    id_turno        INTEGER     NOT NULL,
    ora_inizio      TIMESTAMP   NOT NULL,
    ora_fine        TIMESTAMP   NOT NULL,
    id_dipendente   INTEGER     NOT NULL,
    id_area         INTEGER     NOT NULL,
    CONSTRAINT PK_TURNO             PRIMARY KEY (id_turno),
    CONSTRAINT FKturno_dipendente   FOREIGN KEY (id_dipendente) REFERENCES DIPENDENTE(id_dipendente),
    CONSTRAINT FKturno_area         FOREIGN KEY (id_area)       REFERENCES AREA(id_area),
    CONSTRAINT chk_turno_orario     CHECK (ora_fine > ora_inizio),
    -- Un dipendente non può avere due turni sovrapposti
    CONSTRAINT no_overlap_turno     EXCLUDE USING gist (
        id_dipendente WITH =,
        tsrange(ora_inizio, ora_fine) WITH &&
    )
);


-- ============================================================
--  VISITE MEDICHE
-- ============================================================

CREATE TABLE VISITA_MEDICA (
    id_visita           INTEGER         NOT NULL,
    peso                NUMERIC(10,2),
    diagnosi            VARCHAR(250),
    note_trattamento    VARCHAR(250),
    data_visita         DATE            NOT NULL,
    data_fine           DATE,
    id_animale          INTEGER         NOT NULL,
    id_veterinario      INTEGER         NOT NULL,
    CONSTRAINT PK_VISITA_MEDICA     PRIMARY KEY (id_visita),
    CONSTRAINT FKreferto            FOREIGN KEY (id_animale)     REFERENCES ANIMALE(id_animale),
    CONSTRAINT FKvisita             FOREIGN KEY (id_veterinario) REFERENCES DIPENDENTE(id_dipendente),
    CONSTRAINT chk_visita_peso      CHECK (peso IS NULL OR peso > 0),
    CONSTRAINT chk_visita_date      CHECK (data_fine IS NULL OR data_fine >= data_visita)
);
COMMENT ON COLUMN VISITA_MEDICA.id_veterinario IS 'FK verso DIPENDENTE — deve essere un dipendente con mansione veterinario';
COMMENT ON COLUMN VISITA_MEDICA.data_fine      IS 'NULL = trattamento ancora in corso';


-- ============================================================
--  UTENTI (solo dipendenti con accesso al gestionale)
-- ============================================================

CREATE TABLE UTENTE (
    id_utente           INTEGER      NOT NULL,
    email               VARCHAR(100) NOT NULL,
    password_hash       VARCHAR(250) NOT NULL,
    data_registrazione  DATE         NOT NULL,
    id_dipendente       INTEGER      NOT NULL,
    CONSTRAINT PK_UTENTE        PRIMARY KEY (id_utente),
    CONSTRAINT UQ_UTENTE_EMAIL  UNIQUE (email),
    CONSTRAINT FKaccesso        FOREIGN KEY (id_dipendente) REFERENCES DIPENDENTE(id_dipendente),
    CONSTRAINT UQ_UTENTE_DIP    UNIQUE (id_dipendente)
);
COMMENT ON TABLE  UTENTE              IS 'Account di accesso al gestionale — solo per dipendenti autorizzati';
COMMENT ON COLUMN UTENTE.id_dipendente IS 'Ogni dipendente può avere al massimo un account (vincolo UNIQUE)';


-- ============================================================
--  FORNITORI
-- ============================================================

CREATE TABLE FORNITORI (
    id_fornitore        INTEGER      NOT NULL,
    nome_azienda        VARCHAR(50)  NOT NULL,
    descrizione         VARCHAR(250),
    indirizzo           VARCHAR(100),
    iban                VARCHAR(34),
    id_tipo_fornitura   INTEGER      NOT NULL,
    CONSTRAINT PK_FORNITORI         PRIMARY KEY (id_fornitore),
    CONSTRAINT FKspecializzazione   FOREIGN KEY (id_tipo_fornitura) REFERENCES TIPO_FORNITURA(id_tipo_fornitura),
    CONSTRAINT chk_iban_length      CHECK (iban IS NULL OR LENGTH(iban) BETWEEN 15 AND 34)
);
COMMENT ON COLUMN FORNITORI.iban IS 'IBAN internazionale, lunghezza variabile tra 15 e 34 caratteri';


-- ============================================================
--  BIGLIETTERIA: SCONTRINI E DETTAGLI
-- ============================================================

CREATE TABLE SCONTRINI (
    id_scontrino    INTEGER     NOT NULL,
    data_acquisto   DATE        NOT NULL,
    nome_gruppo     VARCHAR(50),
    num_persone     INTEGER,
    id_utente       INTEGER     NOT NULL,
    CONSTRAINT PK_SCONTRINI         PRIMARY KEY (id_scontrino),
    CONSTRAINT FKgenerazione        FOREIGN KEY (id_utente) REFERENCES UTENTE(id_utente),
    CONSTRAINT chk_num_persone      CHECK (num_persone IS NULL OR num_persone > 0)
);
COMMENT ON COLUMN SCONTRINI.nome_gruppo  IS 'Nome del gruppo scolastico o comitiva, se applicabile';
COMMENT ON COLUMN SCONTRINI.num_persone  IS 'Numero totale di persone del gruppo, se applicabile';


CREATE TABLE DETTAGLIO_SCONTRINI (
    id_dettaglio            INTEGER         NOT NULL,
    quantita                INTEGER         NOT NULL,
    prezzo_pagato_biglietto NUMERIC(10,2)   NOT NULL,
    id_biglietto            INTEGER         NOT NULL,
    id_scontrino            INTEGER         NOT NULL,
    CONSTRAINT PK_DETTAGLIO_SCONTRINI   PRIMARY KEY (id_dettaglio),
    CONSTRAINT FKtariffa                FOREIGN KEY (id_biglietto)  REFERENCES TIPO_BIGLIETTI(id_biglietto),
    CONSTRAINT FKcomposizione           FOREIGN KEY (id_scontrino)  REFERENCES SCONTRINI(id_scontrino),
    CONSTRAINT chk_det_quantita         CHECK (quantita > 0),
    CONSTRAINT chk_det_prezzo           CHECK (prezzo_pagato_biglietto >= 0)
);
COMMENT ON COLUMN DETTAGLIO_SCONTRINI.prezzo_pagato_biglietto IS 'Prezzo storicizzato al momento dell acquisto, indipendente dal listino attuale';


-- ============================================================
--  TRANSAZIONI FINANZIARIE
-- ============================================================

CREATE TABLE TRANSAZIONI (
    id_transazione  INTEGER         NOT NULL,
    tipo            CHAR(1)         NOT NULL,
    importo         NUMERIC(10,2)   NOT NULL,
    data            DATE            NOT NULL,
    descrizione     VARCHAR(250),
    id_categoria    INTEGER         NOT NULL,
    id_utente       INTEGER         NOT NULL,
    id_fornitore    INTEGER,
    id_scontrino    INTEGER,
    CONSTRAINT PK_TRANSAZIONI           PRIMARY KEY (id_transazione),
    CONSTRAINT FKimputazione            FOREIGN KEY (id_categoria)  REFERENCES CATEGORIE_TRANSAZIONI(id_categoria),
    CONSTRAINT FKcreazione              FOREIGN KEY (id_utente)     REFERENCES UTENTE(id_utente),
    CONSTRAINT FKfornitura              FOREIGN KEY (id_fornitore)  REFERENCES FORNITORI(id_fornitore),
    CONSTRAINT FKincasso                FOREIGN KEY (id_scontrino)  REFERENCES SCONTRINI(id_scontrino),
    CONSTRAINT chk_transazione_tipo     CHECK (tipo IN ('E', 'U')),
    CONSTRAINT chk_transazione_importo  CHECK (importo > 0),
    -- Coerenza: entrata → ha scontrino, nessun fornitore | uscita → ha fornitore, nessun scontrino
    CONSTRAINT chk_transazione_coerente CHECK (
        (tipo = 'E' AND id_scontrino IS NOT NULL AND id_fornitore IS NULL)
        OR
        (tipo = 'U' AND id_fornitore IS NOT NULL AND id_scontrino IS NULL)
    )
);
COMMENT ON COLUMN TRANSAZIONI.tipo IS 'E = entrata (incasso biglietteria), U = uscita (pagamento fornitore)';
