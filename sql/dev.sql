# 블로그 테스트 생성 구문
CREATE TABLE blog(
                     blog_id INT AUTO_INCREMENT PRIMARY KEY,
                     writer VARCHAR(16) NOT NULL,
                     blog_title VARCHAR(200) NOT NULL,
                     blog_content VARCHAR(4000) NOT NULL,
                     published_at DATETIME DEFAULT now(),
                     updated_at DATETIME DEFAULT now(),
                     blog_count INT DEFAULT 0
);

INSERT INTO blog VALUES
                     (null, '1번유저', '1번제목', '1번본문', now(), now(), null),
                     (null, '2번유저', '2번제목', '2번본문', now(), now(), null),
                     (null, '3번유저', '3번제목', '3번본문', now(), now(), null);
                     
CREATE TABLE reply(
	reply_id int primary key auto_increment,
    blog_id int not null,
    reply_writer varchar(40) not null,
    reply_content varchar(200) not null,
    published_at datetime default now(),
    updated_at datetime default now()
);

#외래키 설정
alter table reply add constraint fk_reply foreign key (blog_id) references blog(blog_id);

INSERT INTO reply VALUES(null, 2, "댓글쓴사람", "1빠댓글ㅋㅋ", now(), now()),
(null, 2, "짹짹이", "2빠댓글", now(), now()),
(null, 2, "바둑이", "3빠댓글", now(), now()),
(null, 2, "야옹이", "4빠댓글", now(), now()),
(null, 3, "개발고수", "4빠댓글", now(), now());

