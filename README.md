<h1 align="center">
	<img src="https://i.imgur.com/rqOh0KW.png" alt="IA">
</h1>

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
- Proyecto git de [Coeus](https://github.com/TesisLuciaFrancoRenzo/coeus) en un direcotrio contiguo a este proyecto 
(o libreria jar en el directrio lib)

##### Instrucciones Recomendadas
- `gradlew clean`: limpia los directorios del proyecto.   
- `gradlew build`: compila el proyecto.
- `gradlew finalFatJar`: crea un jar con la librería lista para 
usar.  
- `gradlew test`:  ejecuta los test de JUnit.
- `gradlew javadoc`:  compila javadoc.

## Instrucciones de uso
`java -jar ar.edu.unrc.game2048.experiments.TestGenerator [parameters]`

los parámetros deben ser:
- experimentClassNameList: clases con las implementaciones del perceptron, las cuales pueden ser 
`ConfigNTupleBasicLinear_512, ConfigNTupleBasicLinear_32768, 
ConfigNTupleBasicLinearSimplified_512, ConfigNTupleBasicSigmoid_32768, 
ConfigNTupleBasicTanH_32768, ConfigNTupleBasicTanHSimplified_512, 
ConfigNTupleSymmetricLinear_32768, ConfigNTupleSymmetricTanH_32768`
- gamesToPlay: cantidad de partidos a jugar. 
- winRateLimit: porcentaje de tasa de victoria utilizado para finalizar el entrenamiento (-1 es sin límite).
- gamesToPlayPerThreadForStatistic: cantidad de juegos a jugar por procesador en el cálculo de estadísticas.
- saveEvery: intervalo que establece cada cuántas partidas se debe guardar en un archivo, el estado de la red neuronal actual.
- saveBackupEvery: intervalo que establece cada cuántas partidas se debe realizar un una copia de la red neuronal actual.
- simulationsForStatistics: cantidad de simulaciones que se realizan para calcular estadísticas, por procesador.
- tileToWinForStatistics: valor que se considera como ganador, a la hora de ejecutar estadísticas.
- eligibilityTraceLength: longitud máxima que puede tener la traza de elegibilidad
- gammaList: Lista de tasa de descuento
- concurrentLayerList: Lista de valores booleanos que indican si hay o no concurrencia en cada capa
- computeBestPossibleActionConcurrently: Computar la mejor posible acción que me maximice la recompensa final de manera concurrente
- lambdaList: Lista de  decaimiento exponencial de la traza de elegibilidad.
- annealingAlphaList: tiempo de recocido de la taza de aprendizaje
- alphaList: Lista de tasa de aprendizajes
- whenStartToExplore: Número entre 0.0 y 1.0 que multiplica el promedio de los turnos actuales, para determinar desde qué turno se comienza a explorar.
- fixedExplorationRateList: Lista de tasa de explotación, cada valor indica la  probabilidad de que el movimiento actual sea al azar
- explorationRateInitialValueList: Lista de valores iniciales de interpolación para Exploration Rate.
- explorationRateFinalValuesList: Lista de valores finales de interpolación para Exploration Rate.
- explorationRateStartInterpolationList: Lista de valores que indican desde qué partido se va a empezar a atenuar el valor inicial de la interpolación para el Exploration Rate.
- explorationRateFinishInterpolationList: Lista de valores que indican desde qué partido se va a finalizar la atenuación del valor inicial de la interpolación para el Exploration Rate.

##### Ejemplo
Si queremos entrenar una red neuronal usando NTuplas, hasta que gane un 90% de las veces, usamos:
```
java -cp coeus.game2048-1.0.0.jar ar.edu.unrc.game2048.experiments.TestGenerator experimentDirName=NTuple-Timed90 experimentClassNameList=[ConfigNTupleBasicTanH_32768] createLogs=false canCollectStatistics=false repetitions=10 maxTrainingThreads=1 gamesToPlay=1000000 winRateLimit=90.0 gamesToPlayPerThreadForStats=1000 saveEvery=5000 saveBackupEvery=15000 statisticsOnly=false simulationsForStatistics=8 tileToWinForStatistics=2048 runBackupStatistics=true lambdaList=[0.0] eligibilityTraceLength=-1 replacingTraces=true accumulatingTraces=false annealingAlphaList=[400000] alphaList=[0.005] gammaList=[1] concurrentLayerList=[false,false] computeBestPossibleActionConcurrently=false whenStartToExplore=[1.0] fixedExplorationRateList=[0] 
``` 

## Licencia
[![GNU GPL v3.0](http://www.gnu.org/graphics/gplv3-127x51.png)](http://www.gnu.org/licenses/gpl.html)
