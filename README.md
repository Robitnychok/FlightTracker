Flight Tracker

Popis projektu:  
Webová aplikace, která zobrazuje seznam odletů letadel z vybraného letiště v zadaném časovém rozmezí.  
Data jsou získávána z veřejného OpenSky Network API.

---

Použité technologie:

- Java 17  
- Spring Boot  
- Spring MVC  
- Spring Validation  
- Spring Cache (Caffeine)  
- Thymeleaf  
- MapStruct
- REST API  
- Lombok  
- Maven

---

Level 1: REST API  
- Endpoint: /api/departure?airport=EDDF&begin=201801012000&end=201801012100 
- Vrací seznam odletů ve formátu JSON  
- Formát času: yyyyMMddHHmm

Level 2: Webové rozhraní  
- Endpoint: /api/flights/form
- Formulář pro výběr letiště a časového období  
- Výsledky zobrazeny na HTML stránce ve formě tabulky

Level 3:
- Načtená data jsou cachována po dobu 1 hodiny pomocí Caffeine cache  
