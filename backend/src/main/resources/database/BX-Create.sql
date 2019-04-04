CREATE TABLE bx_users (
  user_id int(11) NOT NULL default '0',
  location varchar(250) default NULL,
  age int(11) default NULL,
  country varchar(250) AS substring(location, INSTR(location, ',', INSTR(location, ',') +2) + 2),
  PRIMARY KEY  (user_id)
);

CREATE INDEX IDX_U_NAME ON bx_users(country);

CREATE TABLE bx_books (
  isbn varchar(13) NOT NULL default '',
  book_title varchar(255) default NULL,
  book_author varchar(255) default NULL,
  year_of_publication int(10) default NULL,
  publisher varchar(255) default NULL,
  image_url_s varchar(255) default NULL,
  image_url_m varchar(255) default NULL,
  image_url_l  varchar(255) default NULL,
  PRIMARY KEY (isbn)
);

CREATE TABLE bx_book_ratings (
  user_id int(11) NOT NULL default '0',
  isbn varchar(13) NOT NULL default '',
  book_rating int(11) NOT NULL default '0',
  PRIMARY KEY  (user_id, isbn)
);

CREATE TABLE bx_countries (
 id int(11) AUTO_INCREMENT NOT NULL,
 country_code varchar(2) NOT NULL default '',
 country_name varchar(100) NOT NULL default '',
 PRIMARY KEY (id)
);




