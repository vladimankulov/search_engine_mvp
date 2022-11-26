CREATE TABLE IF NOT EXISTS site
(
    id                          SERIAL,
    status_indexing_of_web_site varchar(100)  not null,
    status_time                 timestamp default now(),
    last_error                  varchar(1000) not null,
    url                         varchar(1000) not null,
    web_site_name               varchar(100)  not null,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS page
(
    id            SERIAL,
    site_model_id mediumint     not null,
    web_site_path varchar(1000) not null,
    error_code    mediumint     not null,
    content       text          not null,
    PRIMARY KEY id,
    FOREIGN KEY (site_model_id) REFERENCES site (id)
);

CREATE TABLE IF NOT EXISTS lemma
(
    id            SERIAL,
    site_model_id mediumint     not null,
    lemma         varchar(1000) not null,
    frequency     mediumint     not null,
    PRIMARY KEY id,
    FOREIGN KEY (site_model_id) REFERENCES site (id)
);

CREATE TABLE IF NOT EXISTS search_index
(
    id       SERIAL,
    page_id  mediumint not null,
    lemma_id mediumint not null,
    rang     float8    not null,
    FOREIGN KEY (page_id) REFERENCES page (id),
    FOREIGN KEY (lemma_id) REFERENCES lemma (id)
);

