# Caso de estudio para la librería Coeus: Juego 2048.
Este caso de prueba basó sus configuraciones en las ideas del paper de Marcin Szubert 
y Wojciech Jaskowski, para así ser capaz de comparar y analizar los resultados finales.

## El Juego
2048 es un juego Solitario de tipo rompecabezas creado por Gabriele Cirulli y 
publicado por primera vez el 9 de marzo de 2014. El juego es open source, con 
licencia MIT. 
El creador del juego se inspiró en los juegos 1024 y Threes para crear 2048, 
por tanto sus reglas son similares.
El objetivo del juego 2048 es formar una ficha con el valor 2048, mediante la 
combinación de fichas de menor número, todas con el valor de las potencias de 2.
El tablero de juego de 2048 es una cuadrícula de 4X4 con 16 casilleros. Al comenzar 
el juego, el tablero tiene 2 fichas con valor 2 o 4  colocadas aleatoriamente. 
A partir de este tablero inicial, las fichas se deben ir moviendo hacia arriba, 
abajo, izquierda o derecha con el fin de combinarlas. Dos fichas se pueden combinar 
si tienen el mismo valor, generando una ficha con la sumatoria de ambos valores. 
Con cada movimiento de las fichas (sin importar si se pueden combinar o no) se genera 
una nueva ficha con valor 2 o 4, que se ubica al azar en los casilleros libres del 
tablero.
La puntuación del juego es la sumatoria de todos los valores de las fichas fusionadas, 
es decir que, por ejemplo, si se combinan dos fichas con valor 8, se genera una nueva
ficha con valor 16 y se suma a la puntuación actual 16 puntos. Cuando se forma la 
ficha con valor 2048 finaliza el juego.
Dado que el juego contiene mucho azar, tanto en la colocación de las nuevas fichas en 
los espacios libres del tablero, como en el valor de dichas fichas (2 o 4), 
es muy difícil hacer una inteligencia artificial determinística que pueda ganar el juego.
Una de las razones de la popularidad masiva del juego es que es muy fácil de aprender, 
pero difícil de dominar. De hecho, el autor del juego admitió que de cientos de 
millones de juegos jugados, sólo se ha ganado un 1%. La dificultad del juego junto 
con la simplicidad de sus reglas lo convierten en un interesante banco de pruebas 
para los métodos de inteligencia artificial.


## Instalación
El proyecto esta construido utilizando Gradle (incorporado en el 
repositorio). 

##### Requisitos
- Java JDK 8 o superior.
- Tener configurada la variable de entorno ***JAVA_HOME***. 

##### Dependencias
- Proyecto git de [Coeus](https://github.com/TesisLuciaFrancoRenzo/coeus) en el mismo directorio 
(o libreria jar en el directrio lib)

##### Instrucciones Recomendadas
- `gradlew clean`: limpia los directorios del proyecto.   
- `gradlew build`: compila el proyecto.
- `gradlew finalFatJar`: crea un jar con la librería lista para 
usar.  
- `gradlew test`:  ejecuta los test de JUnit.
- `gradlew javadoc`:  compila javadoc.
