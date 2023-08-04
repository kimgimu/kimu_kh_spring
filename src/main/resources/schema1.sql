CREATE TABLE IF NOT EXISTS article (
    id int NOT NULL AUTO_INCREMENT,
    title varchar(100) NOT NULL,
    content varchar(1000) NOT NULL,
    PRIMARY KEY (id)
);