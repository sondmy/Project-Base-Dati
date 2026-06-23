-- Inserimento dati per lo schema dello zoo
-- File generato per popolare il database con record realistici

-- STATO_ESISTENZA
INSERT INTO STATO_ESISTENZA (nome, descrizione) VALUES
('LC', 'Rischio minimo (Least Concern)'),
('VU', 'Vulnerabile (Vulnerable)'),
('EN', 'In pericolo (Endangered)'),
('CR', 'In pericolo critico (Critically Endangered)'),
('EW', 'Estinto in natura (Extinct in the Wild)'),
('EX', 'Estinto (Extinct)');

-- HABITAT
INSERT INTO HABITAT (nome, descrizione) VALUES
('Savana', 'Pianura erbosa con alberi sparsi, clima tropicale'),
('Foresta Pluviale', 'Foresta con elevata piovosità e biodiversità'),
('Deserto', 'Area arida con scarsa vegetazione'),
('Tundra', 'Ambiente freddo senza alberi'),
('Oceano', 'Acque marine aperte e profonde'),
('Barriera Corallina', 'Ecosistema marino costiero ricco di vita'),
('Foresta Temperata', 'Foresta con stagioni distinte e alberi decidui');

-- FAMIGLIA_SPECIE
INSERT INTO FAMIGLIA_SPECIE (nome, descrizione) VALUES
('Felidae', 'Mammiferi carnivori, include leoni, tigri, gatti'),
('Canidae', 'Mammiferi carnivori, include lupi, volpi, cani'),
('Elephantidae', 'Grandi mammiferi erbivori provvisti di proboscide'),
('Spheniscidae', 'Uccelli marini non volatori, pinguini'),
('Crocodylidae', 'Rettili acquatici predatori'),
('Giraffidae', 'Mammiferi ruminanti africani a collo lungo');

-- TIPO_AREA
INSERT INTO TIPO_AREA (nome, descrizione) VALUES
('Espositiva', 'Area aperta al pubblico per la visione degli animali'),
('Veterinaria', 'Area riservata alle cure mediche e quarantena'),
('Amministrativa', 'Uffici e servizi per il personale'),
('Ristoro', 'Aree picnic e ristoranti per i visitatori'),
('Magazzino', 'Area per lo stoccaggio di cibo e attrezzature');

-- TIPO_RECINTO
INSERT INTO TIPO_RECINTO (nome, descrizione) VALUES
('Gabbia Aperta', 'Recinto delimitato da reti o sbarre per animali terrestri'),
('Vasca', 'Ambiente acquatico per pesci o mammiferi marini'),
('Voliera', 'Grande struttura chiusa da reti per uccelli'),
('Terrario', 'Ambiente chiuso, spesso riscaldato, per rettili e anfibi'),
('Recinto Panoramico', 'Recinto ampio con barriere naturali (fossati) per grandi erbivori');

-- MANSIONE
INSERT INTO MANSIONE (nome, descrizione) VALUES
('Veterinario', 'Responsabile della salute degli animali'),
('Guardiano', 'Si occupa della nutrizione e pulizia dei recinti'),
('Guida Turistica', 'Accompagna i visitatori spiegando le particolarità degli animali'),
('Cassiere', 'Si occupa della vendita dei biglietti'),
('Amministratore', 'Gestisce il personale e la contabilità'),
('Manutentore', 'Responsabile delle riparazioni strutturali');

-- TIPO_CIBO
INSERT INTO TIPO_CIBO (nome, descrizione) VALUES
('Carne Rossa', 'Tagli di carne bovina o equina'),
('Carne Bianca', 'Pollo e coniglio'),
('Pesce', 'Pesce azzurro e crostacei'),
('Fieno', 'Erba essiccata per erbivori'),
('Frutta e Verdura', 'Mix di vegetali freschi'),
('Mangime Speciale', 'Pellet vitaminizzati per uccelli o rettili');

-- TIPO_FORNITURA
INSERT INTO TIPO_FORNITURA (nome, descrizione) VALUES
('Alimentare', 'Fornitura di cibo per animali'),
('Farmaceutica', 'Medicinali e attrezzature veterinarie'),
('Manutenzione', 'Materiale edile, reti, pompe idrauliche'),
('Cancelleria', 'Materiale da ufficio'),
('Merchandising', 'Prodotti per lo shop dello zoo');

-- TIPO_BIGLIETTO
INSERT INTO TIPO_BIGLIETTO (nome, descrizione, prezzo, attivo) VALUES
('Intero', 'Biglietto standard per adulti (18-64 anni)', 15.00, true),
('Ridotto Bambini', 'Biglietto per bambini (4-17 anni)', 10.00, true),
('Ridotto Senior', 'Biglietto per over 65', 12.00, true),
('Gratuito', 'Bambini sotto i 4 anni e disabili', 0.00, true),
('Famiglia', '2 adulti + 2 bambini', 40.00, true),
('Promo Invernale', 'Sconto speciale per i mesi freddi', 10.00, false);

-- CATEGORIA_TRANSAZIONE
INSERT INTO CATEGORIA_TRANSAZIONE (nome, descrizione, tipo) VALUES
('Vendita Biglietti', 'Incassi dalla biglietteria', 'E'),
('Acquisto Cibo', 'Spese per il cibo degli animali', 'U'),
('Stipendi', 'Pagamento mensile del personale', 'U'),
('Manutenzione Straordinaria', 'Costi per riparazioni impreviste', 'U'),
('Vendita Merchandising', 'Incassi dallo shop dello zoo', 'E'),
('Spese Veterinarie', 'Acquisto farmaci e strumenti medici', 'U');

-- SPECIE
INSERT INTO SPECIE (nome_scientifico, nome_comune, id_stato, id_habitat, id_famiglia_specie) VALUES
('Panthera leo', 'Leone Africano', 2, 1, 1),
('Loxodonta africana', 'Elefante Africano', 3, 1, 3),
('Aptenodytes forsteri', 'Pinguino Imperatore', 1, 4, 4),
('Canis lupus', 'Lupo Grigio', 1, 7, 2),
('Crocodylus niloticus', 'Coccodrillo del Nilo', 1, 2, 5),
('Giraffa camelopardalis', 'Giraffa', 2, 1, 6),
('Panthera tigris altaica', 'Tigre Siberiana', 3, 7, 1);

-- DIETA
INSERT INTO DIETA (id_specie, id_tipo_cibo, quantita_kg_giorno) VALUES
(1, 1, 7.50),
(2, 4, 150.00),
(2, 5, 50.00),
(3, 3, 3.00),
(4, 1, 2.50),
(4, 2, 1.00),
(5, 1, 5.00),
(5, 3, 2.00),
(6, 4, 30.00),
(6, 5, 10.00),
(7, 1, 9.00);

-- AREA
INSERT INTO AREA (nome, metratura, id_tipo_area) VALUES
('Africa Inesplorata', 15000, 1),
('Mondo dei Ghiacci', 3000, 1),
('Predatori Europei', 5000, 1),
('Rettilario Coperto', 1000, 1),
('Clinica Veterinaria Centrale', 500, 2),
('Magazzino Nord', 800, 5),
('Palazzina Uffici', 300, 3);

-- RECINTO
INSERT INTO RECINTO (nome, capienza_massima, id_tipo_recinto, id_area) VALUES
('Savana Esterna', 5, 5, 1),
('Recinto Panoramico 1', 3, 5, 1),
('Vasca Pinguini', 20, 2, 2),
('Area Predatori', 10, 1, 3),
('Terrario Rettili', 8, 4, 4),
('Stalla Erbivori', 6, 5, 1),
('Gabbia Lupi', 2, 1, 3),
('Magazzino Acqua', 2, 2, 5);

-- ANIMALE
INSERT INTO ANIMALE (nome, sesso, vivo, data_nascita, data_arrivo, data_uscita, id_specie) VALUES
('Simba', 'M', true, '2015-05-12', '2016-01-10', NULL, 1),
('Nala', 'F', true, '2016-08-20', '2018-03-15', NULL, 1),
('Dumbo', 'M', true, '2010-11-05', '2012-05-20', NULL, 2),
('Kowalski', 'M', true, '2020-02-14', '2020-06-01', NULL, 3),
('Zanna', 'M', true, '2018-04-10', '2019-02-28', NULL, 4),
('Stella', 'F', false, '2012-09-30', '2014-01-15', '2023-11-05', 4),
('Rex', 'M', true, '2005-07-22', '2008-09-10', NULL, 5),
('Melman', 'M', true, '2017-12-01', '2019-10-12', NULL, 6),
('Shere Khan', 'M', true, '2014-03-18', '2015-08-22', NULL, 7);

-- STORICO_COLLOCAZIONE
INSERT INTO STORICO_COLLOCAZIONE (id_animale, id_recinto, data_inizio, data_fine) VALUES
(1, 1, '2016-01-11', NULL),
(2, 1, '2018-03-16', NULL),
(3, 2, '2012-05-21', NULL),
(4, 3, '2020-06-02', NULL),
(5, 4, '2019-03-01', NULL),
(6, 4, '2014-01-16', '2023-11-05'),
(7, 5, '2008-09-11', NULL),
(8, 6, '2019-10-13', NULL),
(9, 8, '2015-08-23', '2015-09-23'),
(9, 7, '2015-09-24', NULL);

-- DIPENDENTE
INSERT INTO DIPENDENTE (codice_fiscale, nome, cognome, data_nascita, data_assunzione, id_mansione) VALUES
('RSSMRA80A01H501A', 'Mario', 'Rossi', '1980-01-01', '2010-05-15', 1),
('BNCGCM90B12F205X', 'Giacomo', 'Bianchi', '1990-02-12', '2015-03-01', 2),
('VRDLGI85C45D612Y', 'Luigi', 'Verdi', '1985-03-15', '2012-09-10', 3),
('GLLFNC92D56G273Z', 'Francesca', 'Gialli', '1992-04-16', '2018-06-20', 4),
('MRNPLA75E22L219W', 'Paola', 'Marrone', '1975-05-22', '2008-11-05', 5),
('NTNCRL88F10H501K', 'Carlo', 'Neri', '1988-06-10', '2016-01-15', 6);

-- STORICO_STIPENDIO
INSERT INTO STORICO_STIPENDIO (id_dipendente, prezzo_orario, data_inizio, data_fine) VALUES
(1, 25.00, '2010-05-15', '2018-12-31'),
(1, 30.00, '2019-01-01', NULL),
(2, 12.00, '2015-03-01', '2020-12-31'),
(2, 14.00, '2021-01-01', NULL),
(3, 15.00, '2012-09-10', NULL),
(4, 11.00, '2018-06-20', NULL),
(5, 28.00, '2008-11-05', NULL),
(6, 16.00, '2016-01-15', NULL);

-- TURNO
INSERT INTO TURNO (data_giorno, ora_inizio, ora_fine, id_dipendente, id_area) VALUES
('2024-05-01', '2024-05-01 08:00:00', '2024-05-01 16:00:00', 2, 1),
('2024-05-01', '2024-05-01 09:00:00', '2024-05-01 17:00:00', 1, 5),
('2024-05-01', '2024-05-01 08:30:00', '2024-05-01 18:30:00', 4, 7);

-- VISITA_MEDICA
INSERT INTO VISITA_MEDICA (peso, diagnosi, note_trattamento, data_visita, data_fine, id_animale, id_veterinario) VALUES
(190.5, 'Controllo di routine', 'Tutto nella norma', '2023-05-10', '2023-05-10', 1, 1),
(4500.0, 'Infiammazione zampa posteriore', 'Somministrato antinfiammatorio per 5 giorni', '2024-02-15', '2024-02-20', 3, 1),
(35.2, 'Ferita lacero contusa', 'Suturata ferita, antibiotico', '2023-10-25', '2023-11-05', 6, 1);

-- UTENTE
INSERT INTO UTENTE (email, password_hash, data_registrazione, id_dipendente, ruolo) VALUES
('admin@zoopark.it', '$2a$10$/8VMXMUHFPp5F/GVyGgw2OpmWIPEKWTmSN4.ZWhPfNVJMqhOpMzB2', '2010-01-01', 5, 'admin'),
('biglietteria@zoopark.it', '$2a$10$/8VMXMUHFPp5F/GVyGgw2OpmWIPEKWTmSN4.ZWhPfNVJMqhOpMzB2', '2018-06-21', 4, 'cassiere'),
('guardiano@zoopark.it', '$2a$10$/8VMXMUHFPp5F/GVyGgw2OpmWIPEKWTmSN4.ZWhPfNVJMqhOpMzB2', '2010-07-21', 2, 'guardiano'),
('vet.rossi@zoopark.it', '$2a$10$/8VMXMUHFPp5F/GVyGgw2OpmWIPEKWTmSN4.ZWhPfNVJMqhOpMzB2', '2010-05-16', 1, 'veterinario');

-- FORNITORE
INSERT INTO FORNITORE (nome_azienda, descrizione, indirizzo, iban, id_tipo_fornitura) VALUES
('Carni Scelte S.p.A.', 'Fornitore all ingrosso di carne di prima qualità', 'Via Macelli 12, Milano', 'IT12A3456789012345678901234', 1),
('Agricola Verde s.r.l.', 'Produttore di fieno e vegetali bio', 'Strada Campagna 4, Lodi', 'IT98B7654321098765432109876', 1),
('Ittica del Mare', 'Fornitore di pesce fresco e surgelato', 'Porto Commerciale 8, Genova', 'IT45C1234567890123456789012', 1),
('VetPharma', 'Distributore di farmaci veterinari', 'Viale Salute 55, Roma', 'IT55D0000000000000000000000', 2);

-- FORNITORE_CIBO
INSERT INTO FORNITORE_CIBO (id_fornitore, id_tipo_cibo, prezzo) VALUES
(1, 1, 5.20),
(1, 2, 4.80),
(2, 4, 0.55),
(2, 5, 0.75),
(3, 3, 3.20);

-- SCONTRINO
INSERT INTO SCONTRINO (data_acquisto, nome_gruppo, num_persone, id_utente) VALUES
('2024-05-01', NULL, NULL, 2),
('2024-05-01', 'Scuola Elementare Garibaldi', 25, 2),
('2024-05-02', NULL, NULL, 2);

-- DETTAGLIO_SCONTRINO
INSERT INTO DETTAGLIO_SCONTRINO (quantita, prezzo_pagato_biglietto, id_biglietto, id_scontrino) VALUES
(2, 15.00, 1, 1),
(1, 10.00, 2, 1),
(20, 10.00, 2, 2),
(5, 15.00, 1, 2),
(1, 40.00, 5, 3);

-- TRANSAZIONE
INSERT INTO TRANSAZIONE (tipo, importo, data, descrizione, id_categoria, id_utente, id_fornitore, id_scontrino) VALUES
('E', 40.00, '2024-05-01', 'Incasso scontrino 1', 1, 2, NULL, 1),
('E', 275.00, '2024-05-01', 'Incasso scontrino 2', 1, 2, NULL, 2),
('E', 40.00, '2024-05-02', 'Incasso scontrino 3', 1, 2, NULL, 3),
('U', 1500.00, '2024-05-05', 'Fattura acquisto carne', 2, 1, 1, NULL),
('U', 800.00, '2024-05-06', 'Fattura vegetali e fieno', 2, 1, 2, NULL),
('U', 350.00, '2024-05-10', 'Rifornimento antibiotici', 6, 1, 4, NULL);

-- ORDINE_GIORNALIERO
INSERT INTO ORDINE_GIORNALIERO (data_ordine, id_fornitore, id_tipo_cibo, quantita_kg, id_transazione) VALUES
('2024-05-05', 1, 1, 300.00, 4),
('2024-05-06', 2, 4, 1000.00, 5),
('2024-05-06', 2, 5, 200.00, 5),
('2024-06-16', 3, 3, 50.00, NULL);
