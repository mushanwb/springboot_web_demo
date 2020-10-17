create table user (
    id bigint primary key auto_increment,
    username varchar (100),
    encrypt_password varchar (100),
    avatar varchar (255),
    created_at datetime,
    updated_at datetime
) DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_general_ci;