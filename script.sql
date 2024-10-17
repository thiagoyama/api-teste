CREATE TABLE tb_jogo (
    cd_jogo         NUMBER NOT NULL,
    nm_jogo        VARCHAR2(40) NOT NULL,
    ds_jogo        VARCHAR2(255),
    ds_plataforma  VARCHAR2(20),
    dt_lancamento  DATET,
    vl_jogo        NUMBER(10, 2),
    st_multiplayer CHAR(1),
    dt_cadastro    DATETIME NOT NULL,
    dt_atualizacao DATETIME,
    img_url        VARCHAR2(255)
);

ALTER TABLE tb_jogo ADD CONSTRAINT tb_jogo_pk PRIMARY KEY ( codigo );

CREATE SEQUENCE sq_tb_jogo START WITH 1 INCREMENT BY 1 NOCACHE;