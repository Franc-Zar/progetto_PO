# Spring boot rest API OpenWeather 
Il seguente progetto è relativo all'appello di **programmazione a oggetti di gennaio 2020/2021**.
## WeatherService
La nostra applicazione utilizza l'**API** [Current Weather Data](https://openweathermap.org/current#cycle) realizzata da OpenWeather  e manipola i dati ottenuti dalla suddetta per fornire all'utente informazioni riguardanti la **nuvolosità** e la **pressione** della città scelta e, ove specificato, delle città circostanti.
### Utilizzo
L'applicazione viene avviata su **"localhost:8080"** e sfrutta **tre Path**, distinte in funzione dell'operazione richiesta dall'utente:

**Keys:**
*  lat = latitudine
*  lon = longitudine
*  cnt = numero totale di città da analizzare (compresa quella di cui sono date le coordinate)
*  NOTA: se non verranno forniti valori per le keys, l'applicazione ne attribuirà di default

```
localhost:8080/actual?lat="lat"&lon="lon"&cnt="cnt"  
```
fornisce all'utente **pressione** e **nuvolosità attuali** relative alla città scelta, cercata tramite coordinate geografiche (gradi decimali), e alle eventuali ulteriori città limitrofe.

```
localhost:8080/stats/{type}/{period}?lat="lat"&lon="lon"&cnt="cnt"  
```
fornisce all'utente **statistiche** riguardanti la **pressione** o la **nuvolosità** relativa alla città scelta, cercata tramite coordinate geografiche (gradi decimali), e alle eventuali ulteriori città limitrofe.

L'utente dovrà sostituire **{type}** con: 
*  **pressure** --> se vuole conoscere le informazioni relative alla **pressione**
*  **cloud** --> se vuole conoscere le informazioni relative alla **nuvolosità**

l'utente dovrà sostituire **{period}** con il valore numerico della **periodicità** sulla quale effettuare le statistiche (misurato in giorni).

Le **statistiche** consistono in:
* **calcolo della media** 
* **calcolo della varianza**

```
localhost:8080/archive?lat="lat"&lon="lon"&cnt="cnt"  
```
fornisce all'utente lo **storico** dei dati riguardanti la città scelta, cercata tramite coordinate geografiche (gradi decimali), e le eventuali ulteriori città limitrofe.
