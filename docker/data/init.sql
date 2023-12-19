
CREATE TABLE user (
                    id         bigint auto_increment primary key,
                    name       varchar(256) not null,
                    age        int(10) not null,
                    created_at timestamp default CURRENT_TIMESTAMP,
                    updated_at timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);

CREATE TABLE post (
                    id         bigint auto_increment primary key,
                    title    varchar(500),
                    content    varchar(500),
                    author_id  bigint not null,
                    created_at timestamp default CURRENT_TIMESTAMP,
                    updated_at timestamp default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP
);

ALTER TABLE post
    ADD FOREIGN KEY (`author_id`) REFERENCES user (`id`);

insert into user (name,age) values ('spring',20);
insert into user (name,age) values ('webflux',25);

insert into post (title,content,author_id) values ('post1_1','post1_1 content',1);
insert into post (title,content,author_id) values ('post1_2','post1_2 content',1);
insert into post (title,content,author_id) values ('post2_1','post2_1 content',2);
insert into post (title,content,author_id) values ('post2_2','post2_2 content',2);
