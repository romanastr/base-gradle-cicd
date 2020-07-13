CREATE TABLE records
(
    id                bigint primary key auto_increment,
    num               int(4),
    publish_date      date,
    call_time      datetime,
    transcript_length int(4)
);
CREATE INDEX request_time_idx on records (call_time);