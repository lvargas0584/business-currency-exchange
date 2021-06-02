DROP TABLE IF EXISTS EXCHANGE_RATES;

CREATE TABLE EXCHANGE_RATES(
    id INT AUTO_INCREMENT  PRIMARY KEY,
    iso_currency_source VARCHAR(3) NOT NULL,
    iso_currency_target VARCHAR(3) NOT NULL,
    exchange_rate FLOAT NOT NULL,
    status INTEGER DEFAULT NULL
);

--INSERT INTO EXCHANGE_RATES (iso_currency_source,iso_currency_target,exchange_rate,status) VALUES ( 'USD','PEN',3.845,1);
--INSERT INTO EXCHANGE_RATES (iso_currency_source,iso_currency_target,exchange_rate,status) VALUES ( 'PEN','USD',3.838,1);
--INSERT INTO EXCHANGE_RATES (iso_currency_source,iso_currency_target,exchange_rate,status) VALUES ( 'EUR','PEN',4.945,1);
--INSERT INTO EXCHANGE_RATES (iso_currency_source,iso_currency_target,exchange_rate,status) VALUES ( 'PEN','EUR',4.689,1);