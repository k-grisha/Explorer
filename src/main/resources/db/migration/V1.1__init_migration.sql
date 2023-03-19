CREATE TABLE IF NOT EXISTS eth_block
(
    block_number     bigint not null primary key,
    base_fee_per_gas numeric(38, 18),
    miner            varchar(42),
    gas_used         bigint,
    timestamp        bigint not null,
    version          int
);


CREATE TABLE IF NOT EXISTS eth_transaction
(
    transaction_hash         varchar(66) not null primary key,
    block_number             bigint,
    cumulative_gas_used      bigint      not null,
    effective_gas_price      numeric(38, 18),
    from_adr                 varchar(42),
    gas                      bigint      not null,
    gas_price                numeric(38, 18),
    gas_used                 bigint      not null,
    max_fee_per_gas          numeric(38, 18),
    max_priority_fee_per_gas numeric(38, 18),
    status                   integer     not null,
    to_adr                   varchar(42),
    transaction_index        integer     not null,
    type                     integer     not null,
    value                    numeric(38, 18),
    version                  integer
);