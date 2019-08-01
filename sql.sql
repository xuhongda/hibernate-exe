# partition
use self;
create table xx(

    price INTEGER not null

) engine =InnoDB partition by RANGE (price)(

    PARTITION p_1 values less than (2),
    PARTITION p_2 values less than (4),
    PARTITION p_3 values less than (6),
    PARTITION p_4 values less than (8),
    PARTITION p_5 values less than (10),
    PARTITION p_6 values less than (12),
    partition p_7 values less than MAXVALUE
    );

explain select * from xx where price =1;