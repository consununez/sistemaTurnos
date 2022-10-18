# La app

- La aplicación se trata de un sistema que permite administrar la reserva de turnos.
- Permite administrar los datos de prestadores y de usuarios.
- Permite asignar a un usuario un turno con un prestador a una determinada fecha y hora.
- El proyecto fue realizado con Java.

### Objetivo

Desarrolar la aplicación en capas:

- Capa de entidades de negocio: son las clases Java de nuestro negocio modelado a través del paradigma orientado a objetos.
- Capa de acceso a datos (Repository): son las clases que se encargarán de acceder a la base de datos.
- Capa de datos (base de datos): es la base de datos de nuestro sistema modelado a través de un modelo entidad-relación. Utilizaremos la base H2 por su practicidad.
- Capa de negocio: son las clases service que se encargan de desacoplar el acceso a datos de la vista.
- Capa de presentación: son las pantallas web que tendremos que desarrollar utilizando el framework de Spring Boot MVC con los controladores y alguna de estas dos opciones: HTML+JavaScript o React para la vista.
- Excepciones y tests.

### Tecnologías utilizadas

Backend:
JAVA / Spring MVC / Hibernate / H2 / PostrgreSQL / Maven / JUnit

Frontend:
Javascript / HTML / CSS
