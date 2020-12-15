# Spring boot rest API OpenWeather 
Il seguente progetto è relativo all'appello di **programmazione a oggetti di gennaio 2020/2021**.
## WeatherService
La nostra applicazione utilizza l'**API** [Current Weather Data](https://openweathermap.org/current#cycle) realizzata da OpenWeather  e manipola i dati ottenuti dalla suddetta per fornire all'utente informazioni riguardanti la **nuvolosità** e la **pressione** della città scelta e, ove specificato, delle città circostanti.
### Utilizzo
L'applicazione viene avviata su **"localhost:8080"** e sfrutta **tre Path**, distinte in funzione dell'operazione richiesta dall'utente:

* **Keys:**
  * lat = latitudine
  * lon = longitudine
  * cnt = numero totale di città da analizzare (compresa quella di cui sono date le coordinate)
  
  <br/> NOTA: se non verranno forniti valori per le keys, l'applicazione ne attribuirà di default

1)
Tipo | Path | 
---- | ---- | 
GET | localhost:8080/actual?lat="lat"&lon="lon"&cnt="cnt" |

fornisce all'utente **pressione** e **nuvolosità attuali** relative alla città scelta, cercata tramite coordinate geografiche (gradi decimali), e alle eventuali ulteriori città limitrofe.

2)
Tipo | Path | 
---- | ---- | 
GET | localhost:8080/stats/{type}/{period}?lat="lat"&lon="lon"&cnt="cnt" |

fornisce all'utente **statistiche** riguardanti la **pressione** o la **nuvolosità** relativa alla città scelta, cercata tramite coordinate geografiche (gradi decimali), e alle eventuali ulteriori città limitrofe. 

* L'utente dovrà sostituire **{type}** con: 
  * **pressure** --> se vuole conoscere le informazioni relative alla **pressione**
  * **cloud** --> se vuole conoscere le informazioni relative alla **nuvolosità**

* L'utente dovrà sostituire **{period}** con:
  * il valore numerico della **periodicità** sulla quale effettuare le statistiche (misurato in giorni).

* Le **statistiche** consistono in:
  * **calcolo della media** 
  * **calcolo della varianza**

Le città vengono mostrate all'utente ordinatamente rispetto alla **media** del **{type}** selezionato; <br/>
viene inoltre evidenziata la città con la **varianza massima**


3)
Tipo | Path | 
---- | ---- | 
GET | localhost:8080/archive?lat="lat"&lon="lon"&cnt="cnt" |

fornisce all'utente lo **storico** dei dati riguardanti la città scelta, cercata tramite coordinate geografiche (gradi decimali), e le eventuali ulteriori città limitrofe.

* I dati restituiti dall'applicazione hanno i seguenti formati: 
  * **informazioni attuali**
<br/> ![2020-12-15 (2)](https://user-images.githubusercontent.com/75085155/102226559-cf870800-3ee8-11eb-9c5a-c2112578e329.png)  


### UML 
**Casi d'uso**
![UseCase_ServizioMeteo](https://user-images.githubusercontent.com/75085155/102118385-eb829f00-3e3f-11eb-81cf-cf6f266c6497.png)

