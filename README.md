This is test project using H2 embedded database, Spring Boot 2x and Angular 8

SQL dump was downloaded from http://www2.informatik.uni-freiburg.de/~cziegler/BX/
Scripts were modified to use in H2 database and are located in resources/database folder. Scripts are automatically 
executed with backend.
 

In order to make query faster I used computed columns / function based 
indexing (http://www.h2database.com/html/features.html#computed_columns) in BX-Create.sql bx_users table.


SQL dump contained broken encoded characters, so I fixed BX-Books.sql encoding problems using my own script, based on https://www.i18nqa.com/debug/utf8-debug.html table.


Run backend:

In terminal
cd Imdb/backend
mvn clean install
mvn spring-boot:run
Backend runs on http://localhost:8088/


Run fronend (For that Node.js should be installed):

In terminal 
cd Imdb/frontend
npm init > installs all dependencies frontend needs to serve the app
In the same directive type
ng serve
Frontend runs on http://localhost:4200/