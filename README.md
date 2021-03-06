
# Spring boot rest API OpenWeather 
Il seguente progetto è relativo all'appello di **programmazione a oggetti di gennaio 2020/2021**.
## Autori 
* Luigi Smargiassi 50%
* Francesco Zaritto 50%
## WeatherService
La nostra applicazione utilizza l'**API** [Current Weather Data](https://openweathermap.org/current#cycle) realizzata da OpenWeather  e manipola i dati ottenuti dalla suddetta per fornire all'utente informazioni riguardanti la **nuvolosità** e la **pressione** della città scelta e, ove specificato, delle città circostanti.
### Utilizzo
L'applicazione viene avviata su **"localhost:8080"** e sfrutta diverse **Path**, distinte in funzione dell'operazione richiesta dall'utente:

* **Keys:**
  * lat = latitudine
  * lon = longitudine
  * cnt = numero totale di città da analizzare (compresa quella di cui sono date le coordinate)
  
  <br/> NOTA: se non verranno forniti valori per le keys, l'applicazione ne attribuirà di default,nel caso in cui le keys non rispettino
  i limiti,ovvero quelli naturali per latitudine e longitudine, e il limite di massimo 50 per il contatore apparirà un messaggio
  d'errore al posto della canonica lista di oggetti. Questo non sostituisce la console come canale principale per la segnalazione
  degli errori,ma migliora la raggiungibilità della stessa,come segnala il messaggio che appare nel caso gli errori non dipendano
  direttamente dalla richiesta fatta dall'utente,ma dall'API OpenWeather stesso o dagli archivi in locale.
  
1)
Tipo | Path | 
---- | ---- | 
GET | localhost:8080/" | 

fornisce all'utente un breve "recap" delle funzionalità consentite dall'applicazione.

2)
Tipo | Path | 
---- | ---- | 
GET | localhost:8080/actual?lat="lat"&lon="lon"&cnt="cnt" |

fornisce all'utente **pressione** e **nuvolosità attuali** relative alla città scelta, cercata tramite coordinate geografiche (gradi decimali), e alle eventuali ulteriori città limitrofe.

3)
Tipo | Path | 
---- | ---- | 
POST | localhost:8080/stats/{type}/{period} |

  * fornisce all'utente **statistiche** riguardanti:
    * **pressione** 
    * **nuvolosità** 
    * **entrambe contemporaneamente** <br/>
    
relative alla città scelta, cercata tramite coordinate geografiche (gradi decimali), e alle eventuali ulteriori città limitrofe. <br/>

I parametri di ricerca sono forniti all'applicazione tramite un **Request body** che ha il seguente formato:

<br/> ![2020-12-16 (21)](https://user-images.githubusercontent.com/75085155/102383825-447f3e00-3fcc-11eb-9251-c7cc9127e6e4.png)

* L'utente dovrà sostituire **{type}** con: 
  * **pressure** --> se vuole conoscere le informazioni relative alla **pressione**
  * **cloud** --> se vuole conoscere le informazioni relative alla **nuvolosità**
  * **all** --> se vuole conoscere le informazioni di media e varianza relative contemporaneamente a **pressione** e **nuvolosità**

* L'utente dovrà sostituire **{period}** con:
  * il valore numerico della **periodicità** sulla quale effettuare le statistiche (misurato in giorni).

* Le **statistiche** consistono in:
  * **calcolo della media** 
  * **calcolo della varianza**

* Le città vengono mostrate all'utente ordinatamente rispetto alla **media**, nel caso in cui **{type}** sia un singolo parametro **(cloud o pressure)** ; <br/>
in tal caso viene inoltre evidenziata la città con la **varianza massima** 


4)
Tipo | Path | 
---- | ---- | 
GET | localhost:8080/archive?lat="lat"&lon="lon"&cnt="cnt" |

fornisce all'utente lo **storico** dei dati riguardanti la città scelta, cercata tramite coordinate geografiche (gradi decimali), e le eventuali ulteriori città limitrofe.


5)
Tipo | Path | 
---- | ---- | 
GET | localhost:8080/monitored |

fornisce all'utente la lista di città attualmente monitorate dall'applicazione, al fine di effettuare le statistiche (l'applicazione monitora cinque città di default).


6)
Tipo | Path | 
---- | ---- | 
POST | localhost:8080/setmonitor | 

permette all'utente di aggiungere una città di sua scelta nell'elenco di monitoraggio.

L'applicazione verificherà che tale città corrisponda ai parametri passati, sia già monitorata e, in caso positivo, inizializzerà un archivio dello storico corrispondente, il cui primo dato rappresenta la situazione di **nuvolosità** e **pressione** attuali al momento di tale chiamata; 
l'utente, inoltre, verrà avvisato dell'esito positivo della sua richiesta o, in caso contrario, verranno indicati i motivi del fallimento della suddetta. <br/>

I parametri di ricerca sono forniti all'applicazione tramite un **Request body** che ha il seguente formato:
<br/> ![2020-12-21 (3)](https://user-images.githubusercontent.com/75085155/102772824-799ede00-4388-11eb-87d8-2f19cecab088.png)

7)
Tipo | Path | 
---- | ---- | 
POST | localhost:8080/removemonitor | 

permette all'utente di rimuovere una città di sua scelta nell'elenco di monitoraggio.

L'applicazione verificherà che tale città corrisponda ai parametri passati, sia effettivamente monitorata e, in caso positivo, eliminerà il corrispondente archivio dello storico. 
L'utente, inoltre, verrà avvisato dell'esito positivo della sua richiesta o, in caso contrario, verranno indicati i motivi del fallimento della suddetta. <br/>

I parametri di ricerca sono forniti all'applicazione tramite un **Request body** che ha il seguente formato:
<br/> ![2020-12-21 (3)](https://user-images.githubusercontent.com/75085155/102772824-799ede00-4388-11eb-87d8-2f19cecab088.png)


* I dati restituiti dall'applicazione, per le varie richieste, hanno i seguenti formati: 
  
  * **informazioni attuali**
<br/> ![2020-12-15 (2)](https://user-images.githubusercontent.com/75085155/102226559-cf870800-3ee8-11eb-9c5a-c2112578e329.png) 
  
  * **media e varianza pressione**
<br/>![2020-12-15 (6)](https://user-images.githubusercontent.com/75085155/102270380-3246c680-3f1e-11eb-98c6-27d74a22e3f7.png)
  
  * **media e varianza nuvolosità**
<br/> ![2020-12-15 (8)](https://user-images.githubusercontent.com/75085155/102271207-62429980-3f1f-11eb-8553-a5c016e753fb.png)
  
  * **informazioni dallo storico (es. period = 3)** 
<br/> ![2020-12-15 (10)](https://user-images.githubusercontent.com/75085155/102271588-e432c280-3f1f-11eb-8430-5d9aef011ef1.png)
 
  * **mostra città monitorate**
<br/> ![2020-12-21 (8)](https://user-images.githubusercontent.com/75085155/102773653-e8306b80-4389-11eb-84c7-46968b696107.png)
  
  * **esiti vari riguardo la richiesta di monitoraggio:** 
<br/> ![2020-12-21 (4)](https://user-images.githubusercontent.com/75085155/102773137-047fd880-4389-11eb-996d-ecc37ed68a6b.png)
<br/> ![2020-12-21 (5)](https://user-images.githubusercontent.com/75085155/102773257-3c871b80-4389-11eb-82e6-b1e5fa1a804a.png)
<br/> ![2020-12-21 (7)](https://user-images.githubusercontent.com/75085155/102773440-83751100-4389-11eb-9901-44a397ea3517.png)
  * **esiti vari riguardo la richiesta di interruzzione monitoraggio**
<br/> ![2020-12-21 (11)](https://user-images.githubusercontent.com/75085155/102820982-77fc0700-43d6-11eb-9631-7ec96b9f8bf0.png)
<br/> ![2020-12-21 (9)](https://user-images.githubusercontent.com/75085155/102820998-834f3280-43d6-11eb-8e85-1d1b2951485d.png)



### UML 
   * **Casi d'uso**
     * manipolazione dati meteo
<br/> ![UseCase_ServizioMeteo](https://user-images.githubusercontent.com/75085155/102118385-eb829f00-3e3f-11eb-81cf-cf6f266c6497.png)
     * configurazioni applicazione
<br/> ![ddd](https://user-images.githubusercontent.com/75085155/102835051-fc11b700-43f5-11eb-9bef-5515dd76131c.jpg)

* **Classi:**
  * Main
<br/> ![main](https://user-images.githubusercontent.com/75085155/102903460-a9291580-4470-11eb-8a78-5c9d9e7328c2.png)
  
  * Controller
<br/> ![controller](https://user-images.githubusercontent.com/75085155/102903451-a75f5200-4470-11eb-8550-23af2393fe18.png)

  * Model
<br/> ![model](https://user-images.githubusercontent.com/75085155/102903467-ac240600-4470-11eb-8422-978e53429a7d.png)
  
  * Service
<br/> ![service](https://user-images.githubusercontent.com/75085155/102903474-adedc980-4470-11eb-9f66-9762c50c0b24.png)
  
  * Utilities
<br/> ![util](https://user-images.githubusercontent.com/75085155/102903485-b1815080-4470-11eb-9780-466759be749b.png)


* **Sequenze** 
  * info attuali
<br/> ![new_diagram](https://user-images.githubusercontent.com/75085155/102635433-97373200-4153-11eb-9206-809f925fb84c.png)

  * info storiche 
<br/> ![uuouo](https://user-images.githubusercontent.com/75085155/102635350-7969cd00-4153-11eb-96eb-bc0a62de62f8.png)

  * chiamata isolata archivio
<br/> ![lalal](https://user-images.githubusercontent.com/75085155/102637519-6ad0e500-4156-11eb-96f2-d06249cb6731.png)

  * update Archivio
 <br/> ![lelelel](https://user-images.githubusercontent.com/75085155/102643247-4299b400-415f-11eb-8b00-10ac6147f844.png)



