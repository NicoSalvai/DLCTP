INSERT INTO site (id, title, filepath,processed)
values(1,'','', false);

INSERT INTO word (id, word, n, tfmax)
values(1,'',0,1.5);

INSERT INTO post (id, site_id, word_id, tf)
values(1,1,1,1.5);

--UPDATE DOCUMENTO
UPDATE site
SET processed = true
WHERE id = 0 OR title = '';






SELECT max(id)
FROM word;

SELECT max(id)
FROM post;

SELECT max(id)
FROM site;
