INSERT INTO document (id, title, content) 
values(1 ,'testTitle1', 'testContentInsertLoremIpsum1'),
(2 ,'testTitle2', 'testContentInsertLoremIpsum2'),
(3 ,'testTitle3', 'testContentInsertLoremIpsum3');


INSERT INTO document (id, title, content)
values(1,'','');

INSERT INTO vocabulario (id, word, n, tfmax)
values(1,'',0,1.5);

INSERT INTO posteo (id, word_id, document_id, tf)
values(1,1,1,1.5);

