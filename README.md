# aurelia-spring-app
Application starter basée sur Aurelia JS et Spring MVC en CORS

# Ce starter contient
* Une configuration complète de Spring security stateless CORS
* Inclusion de Bootstrap CSS + un menu vertical pour la navigation
* Une configuration complète de Spring Data avec inclusion de QueryDSL + des fonctions utilitaires pour la pagination
* Une configuration complète pour gérer le multi-tenant (proposé: schéma/database => à décommenter)
* une gestion des utilisateurs type RBAC (utilisateurs/groupes/permissions) couplée à Spring security avec les écrans d'administration associés
* Fonctionnalité de reset et changement de password avec envoi de mail (avec utilisation de velocity)
* Une page 'about' générée via un goal maven pour lister rapidement l'ensemble les dépendances du projet
* Packaging de l'application (externalisation des properties et template de mails) + bundle aurelia


# Pré-requis
Java7 et MySQL (base utilisée par défaut)

# Installation
Suivre les _readme_ des 2 projets
