CREATE TABLE SITE (
    id INTEGER NOT NULL,
    title VARCHAR(50) NOT NULL,
    filepath VARCHAR NOT NULL,
    processed BOOLEAN DEFAULT(false),
    CHECK(id >= 1),
    PRIMARY KEY(id)
);


CREATE TABLE WORD (
    id INTEGER NOT NULL,
    word VARCHAR(50) NOT NULL,
    n INTEGER,
    tfmax INTEGER,
    CHECK(id >= 1),
    PRIMARY KEY(id)
);


CREATE TABLE POST (
    id INTEGER NOT NULL,
    site_id INTEGER NOT NULL,
    word_id INTEGER NOT NULL,
    tf INTEGER NOT NULL,
    CHECK(id >= 1),
    PRIMARY KEY(id),
    FOREIGN KEY (word_id) REFERENCES vocabulario(id),
    FOREIGN KEY (document_id) REFERENCES document(id)
);


---ELIMINAR BASE DE DATOS

DROP TABLE POST, WORD, SITE CASCADE;