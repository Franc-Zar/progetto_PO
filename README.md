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
  <br/> Un breve recap delle funzionalità presenti nell'applicazione è visualizzabile richiamando la path "localhost:8080/"

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
  * **clouds** --> se vuole conoscere le informazioni relative alla **nuvolosità**

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
  * **media e varianza pressione**
<br/>![2020-12-15 (6)](https://user-images.githubusercontent.com/75085155/102270380-3246c680-3f1e-11eb-98c6-27d74a22e3f7.png)
  * **media e varianza nuvolosità**
<br/> ![2020-12-15 (8)](https://user-images.githubusercontent.com/75085155/102271207-62429980-3f1f-11eb-8553-a5c016e753fb.png)
  * **informazioni dallo storico (period = 3)** 
<br/> ![2020-12-15 (10)](https://user-images.githubusercontent.com/75085155/102271588-e432c280-3f1f-11eb-8430-5d9aef011ef1.png)



### UML 
   * **Casi d'uso**
<br/> ![UseCase_ServizioMeteo](https://user-images.githubusercontent.com/75085155/102118385-eb829f00-3e3f-11eb-81cf-cf6f266c6497.png)
* **Classi:**
  * Main
<br/> ![2020-12-16 (7)](https://user-images.githubusercontent.com/75085155/102328364-44108400-3f87-11eb-9840-3eb6f2f52a94.png)
  * Controller
<br/> ![2020-12-16 (8)](https://user-images.githubusercontent.com/75085155/102328698-b2554680-3f87-11eb-92d6-4a03285cc5a6.png)
  * Model
<br/> ![2020-12-16 (9)](https://user-images.githubusercontent.com/75085155/102328899-f7797880-3f87-11eb-827d-f819573e09b3.png)
  * Service
<br/> ![2020-12-16 (10)](https://user-images.githubusercontent.com/75085155/102329007-1b3cbe80-3f88-11eb-925f-e737a43d5e90.png)

* **Sequenze** 
  * info attuali
<br/> ![2020-12-16 (12)](https://user-images.githubusercontent.com/75085155/102338614-5e9d2a00-3f94-11eb-969f-53b271aaa858.png)
  * info storiche 
<br/> ![2020-12-16 (14)](https://user-images.githubusercontent.com/75085155/102340788-36fb9100-3f97-11eb-9981-6ef5f81e2abd.png)
  * chiamata isolata Archivio 
<br/> ![2020-12-16 (17)](https://user-images.githubusercontent.com/75085155/102343055-5ea02880-3f9a-11eb-9250-44e7e1b66142.png)



