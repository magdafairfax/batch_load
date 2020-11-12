DROP  TABLE IF EXISTS STOCK_DAILY_DATA;

CREATE TABLE  `stock_daily_data` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `symbol` varchar(10) DEFAULT NULL,
    `date` datetime DEFAULT NULL,
    `open` float  DEFAULT NULL,
    `high` float DEFAULT NULL,
    `low` float DEFAULT NULL,
    `close` float DEFAULT NULL,
    `volume` int(11) DEFAULT NULL,
    PRIMARY KEY (`id`)
);
CREATE INDEX `idx_symbol1` ON STOCK_DAILY_DATA(symbol)
