-- begin RESTAPIFRONTENDSERVICE_ORDER
create table RESTAPIFRONTENDSERVICE_ORDER (
    ID varchar(36) not null,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER_ varchar(255),
    ITEMS varchar(255),
    DATE_ varchar(255),
    DESCRIPTION varchar(255),
    --
    primary key (ID)
)^
-- end RESTAPIFRONTENDSERVICE_ORDER
