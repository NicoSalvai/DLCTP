CREATE TABLE document (
id INTEGER NOT NULL,
title VARCHAR(50) NOT NULL,
content VARCHAR NOT NULL,
CHECK(id >= 1),
PRIMARY KEY(id)
);


CREATE TABLE vocabulario (
id INTEGER NOT NULL,
word VARCHAR(50) NOT NULL,
CHECK(id >= 1),
PRIMARY KEY(id)
);


CREATE TABLE posteo (
id INTEGER NOT NULL,
word_id INTEGER NOT NULL,
document_id INTEGER NOT NULL,
tf FLOAT NOT NULL,
CHECK(id >= 1),
PRIMARY KEY(id),
FOREIGN KEY (word_id) REFERENCES vocabulario(id),
FOREIGN KEY (document_id) REFERENCES document(id)
);

