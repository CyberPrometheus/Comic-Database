# Backend

### Gradle Version
* Gradle 8.8

### Java Version 
* JDK 21, set Enviroment Variable 'JAVA_HOME' to directory where JDK 21 is stored

### Dependencies
* H2 Database
* Lombok
* Spring Framework
* Hibernate


### H2 Database
* Access URL: http://localhost:8080/h2-console
* User: sa
* Password: 


## Initialscript
* Enable: backend/src/main/resources/application.properties set spring.sql.init.mode=always
* Disable backend/src/main/resources/application.properties set spring.sql.init.mode=never


## SQL Logger
* Go to backend/src/main/resources/application.properties
* Enable: logging.level.org.hibernate.SQL=DEBUG; logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE; spring.jpa.properties.hibernate.format_sql=true
