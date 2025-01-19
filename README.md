# LiterAlura

Este proyecto es parte de un *Challenge* de Alura Latam donde se debe crear una aplicación llamada «LiterAlura» utilizando IntelliJ IDEA, Spring Initializr, Postgres y la API Gutendex. Este *Challenge* pone en práctica lo que hemos aprendido sobre Java.

## Preparación

LiterAlura permite que el usuario busque un libro con la ayuda de Gutendex. Antes de poder utilizar la aplicación, no solo se necesita IntelliJ IDEA para ejecutarlo pero también Postgres para crear una base de datos. Esta base de datos almacenará los libros buscados. Además, para poder conectarse a la base de datos, hay que seguir los siguientes pasos:

1. Haz clic en el botón de Inicio y busca "Editar la variables de entorno del sistema"
2. Haz clic en el botón "Variables de entorno..."
3. Agrega las siguientes variables de usuario:
   a. Nombre: "DB_HOST", valor: "localhost" o "localhost:[numero de puerto elegido al instalar Postgres]"
   b. Nombre: "DB_NAME", valor: "literalura"
   c. Nombre: "DB_USER", valor: "[nombre de usuario elegido al instalar Postgres; por defecto, el nombre de usuario es 'postgres']"
   d. Nombre: "DB_PASSWORD", valor: "[contraseña elegida al instalar Postgres]"
4. Haz clic en "Aceptar" y después reinicia el equipo.

## Ejecución

LiterAlura ofrece seis opciones:

1. La opción 1 permite que el usuario busque un libro. El usuario puede escribir el título completo o parte del título. La aplicación presentará el primer resultado que da la API, mostrando el título, el autor, el idioma y el número de descargas del libro. El libro después quedará registrado en la base de datos.
2. La opción 2 lista los libros ya registrados, mostrando el título, el autor, el idioma y el número de descargas de cada uno.
3. La opción 3 lista los autores de los libros ya registrados, mostrando el nombre, el año de nacimiento, el año de fallecimiento y los libros registrados de cada uno.
4. La opción 4 lista los autores que estuvieron vivos en un año determinado (escrito por el usuario), mostrando el nombre, el año de nacimiento, el año de fallecimiento y los libros registrados de cada uno.
5. La opción 5 lista los libros registrados escritos en un idioma determinado. El usuario tiene cuatro opciónes: es - español, en - inglés, fr - francés y pt - portugués. Se muestran el título, el autor, el idioma y el número de descargas de cada libro.
6. La opción 0 termina la ejecución de la aplicación.
