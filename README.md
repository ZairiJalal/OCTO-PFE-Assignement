## Architecture :
![SPr](https://user-images.githubusercontent.com/58845279/144608101-ba7df2c0-8161-4b2e-9ebd-fa45fad6ecab.PNG)
## Diagramme de classes des entités :
![Présentation sans titre (6)](https://user-images.githubusercontent.com/58845279/144608202-4f732920-5785-49e1-873f-f53dc75751b6.png)
## Description :

Un virement est un transfert d'argent d'un compte emetteur vers un compte bénéficiaire ...

**Besoin métier :** 

Ajouter un nouveau usecase versement. Le Versement est un dépôt d'agent sur un compte donné . 

Le versement est une opération trés utile lors des transfert de cash .
 
Imaginez que vous allez à une agence avec un montant de 1000DH et que vous transferez ça en spécifiant le RIB souhaité .
 
L'identifiant fonctionnel d'un compte dans ce cas préçis est le RIB .  


## Assignement :

* Le code présente des anomalies de qualité (bonnes pratiques , abstraction , lisibilité ...) et des bugs . 
    * localiser le maximum 
    * Essayer d'améliorer la qualité du code .    
    * Essayer de résoudre les bugs détectés. 
* Implementer le use case `Versement` 
* Ajouter des tests unitaires.  
* **Nice to have** : Ajouter une couche de sécurité 

## How to use 
To build the projet you will need : 
* Java 11+ 
* Maven

Build command : 
```
mvn clean install
```

Run command : 
```
./mvnw spring-boot:run 
## or use any prefered method (IDE , java -jar , docker .....)
```

## How to submit 
* Fork the project into your personal gitlab space .    
* Do the stuff .
* Send us the link .
