# Registration service

Ce projet est une API Springboot exposant des services de gestion d'utilisateurs.

## Installation et lancement

Ce projet nécessite d'avoir Java 11+ et Maven d'installé sur la machine.

Vérification de l'installation de Java et Maven dans l'invite de commande :
```
$ java -version
java version "11.0.8" 2020-07-14 LTS
Java(TM) SE Runtime Environment 18.9 (build 11.0.8+10-LTS)
Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.8+10-LTS, mixed mode)

$ mvn -v
Apache Maven 3.8.2 (ea98e05a04480131370aa0c110b8c54cf726c06f)
Maven home: C:\Program Files (x86)\apache-maven-3.8.2
Java version: 11.0.8, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-11.0.8
Default locale: fr_FR, platform encoding: Cp1252
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
```

Cloner le projet sur la machine, se placer à la racine du projet avec un invite de commande et lancer l'application avec :
```
$ mvn clean spring-boot:run
```

L'application est lancée par défaut sur le port 8080. Ce port est visible dans les log au lancement de l'application :
```
INFO 2924 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
```

Pour lancer les tests unitaires de l'application, exécuter :
```
mvn test
```

## Modèle

Un utilisateur est définis par des attributs obligatoires :

- id					: Identifiant unique de l'utilisateur, généré automatiquement à la création de l'utilisateur
- name					: Nom de l'utilisateur
- birthdate				: Date de naissance de l'utilisateur (format AAAA-MM-JJ)
- countryOfResidence	: Pays de résidence de l'utilisateur

Et des attributs optionnels :

- phoneNumber			: Numéro de téléphone de l'utilisateur (format +33 1 01 01 01 01 ou 01 01 01 01 01 )
- gender				: Sexe de l'utilisateur (MALE ou FEMALE)

## Services / Utilisation de l'API

### Afficher les détails de tous les utilisateurs enregistrés
```
GET localhost:8080/users
```

### Enregistrement d'un nouvel utilisateur
```
POST localhost:8080/users
```
Le body de la requête doit contenir un objet au format JSON avec au moins les attributs "name", "birthdate" et "countryOfResidence" de l'utilisateur :
```
{
    "name": "Louis de Funès",
    "birthdate": "1914-07-31",
    "countryOfResidence": "France",
    "phoneNumber": "0101010101",
    "gender": "MALE"
}
```
Ce service renvoi les données de l'utilisateur créé au format JSON, dont l'id généré pour cet utilisateur.

### Afficher les détails d'un utilisateur
```
GET localhost:8080/users/id={id}
```
Remplacer {id} par l'id de l'utilisateur voulu.

### Afficher les utilisateurs possédant le nom {name}
```
GET localhost:8080/users/name={name}
```
Affiche la liste des utilisateurs avec le nom {name}, ne prend pas en compte les majuscules et les espaces.

### Exemples

La collection Postman *RegistrationApplication.postman_collection.json* contient des exemples d'appel REST vers cette application.

## Organisation des sources

Les sources du projet sont présentent dans le dossier src/main/java/registration.

A la racine se trouve le fichier de la classe principale de l'application *RegistrationApplication*.

Le dossier *controller* contient les controleurs de l'application définissant les différents services.

Le dossier *exceptions* contient les différentes exceptions personnalisées du projet.

le dossier *logging* contient l'implémentation des Aspect de Spring AOP pour ce projet.

le dossier *model* contient la définition des objets enregistrés en BDD.

Enfin, les classes contenant les tests unitaires sont présentes dans le dossier src/test/java/registration.

## Points à améliorer

- Méthode de validation des attributs des utilisateurs : https://www.baeldung.com/spring-boot-bean-validation

- Améliorer les tests unitaires : les tests ne doivent pas effectuer de modifications durables sur la BDD (cf tests de création d'utilisateur).

- Organisation des dossiers de source en suivant un modèle plus conventionnel.

## Fonctionnalités à ajouter

- Services complémentaires de gestion d'un utilisateur : mise à jour des données d'un utilisateur (service PUT), suppression d'un utilisateur (service DELETE)

- Affichage du contenu des requêtes avec Spring AOP : https://www.baeldung.com/spring-aop

- Ajout d'une base MongoDB pour stocker les utilisateurs

## Documentations

Sources utilisées pour la création du projet :

Création de services REST avec Spring : https://spring.io/guides/tutorials/rest/

Tester les API avec Springboot : https://openclassrooms.com/fr/courses/6900101-creez-une-application-java-avec-spring-boot/7078023-testez-votre-api-avec-spring-boot

Logger le temps d'exécution des requêtes : https://github.com/krushnaDash/spring-aop-log
