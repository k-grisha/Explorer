CREATE TABLE IF NOT EXISTS reward
(
    eth_block  bigint not null ,
    miner_address varchar(42),
    builder_name  varchar(100),
    block_reward  numeric(38, 18),
    mev_reward    numeric(38, 18),
    mev           boolean,

    FOREIGN KEY (eth_block) REFERENCES eth_block (block_number)
);

