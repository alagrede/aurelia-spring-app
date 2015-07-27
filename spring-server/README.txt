
# Importer le projet dans eclipse
Dans une console à la racine du projet:
mvn eclipse:clean
mvn eclipse:eclipse -DdownloadSources

# Générer les meta-models (pour QueryDSL)
mvn generate-sources

Dans Eclipse:
 Import > Existing project into workspace


# Installation et configuration

1/ Créer un lanceur Eclipse avec spring-loaded (Hot Reload Java):
  - Copier le jar de spring-loaded dans D:/
  - Dans Eclipse:
 	* Run configuration > Maven build (new)
 	* 	Base directory: ${workspace_loc:/spring-template}
 	*   Goals: tomcat7:run
 	* Onglet JRE:
 	*   VM arguments: -javaagent:D:\springloaded-1.2.0.RELEASE.jar -noverify
    * Onglet Common:
    *   Cocher Run et Debug dans "Display favorite menu"

  
2/ Installer Lombok (génération auto getter/setter...)
 - Copier le jar lombok.jar dans le dossier eclipse/
 - Rajouter dans eclipse.ini les lignes suivantes en fin de fichier:
   
   -javaagent:D:\apps\eclipse-jee-kepler-SR2-win32-x86_64\lombok.jar
   -Xbootclasspath/a:D:\apps\eclipse-jee-kepler-SR2-win32-x86_64\lombok.jar
 


# Générer la page de listings des licences
mvn clean install -Pcreate-license-list

     
# Activer le multitenant (sur plusieurs base de données):
-> Jusqu'à la connexion l'utilisateur est sur la base default_db.
   Le tenant est ensuite stocké dans la connexion spring security.

La correspondance tenant/utilisateur est contenue dans la table domaingroup (utilise le username comme clé):
insert into domaingroup values(1, 'tenant1', 'admin');
insert into domaingroup values(2,'tenant2', 'flo');


1/ activer la conf:
  Décommenter le ligne dans applicationContext.xml : 
    <import resource="classpath*:domain-context-multitenant.xml" />
  Commenter les lignes dans applicationContext.xml : 	
    <import resource="classpath*:dataSource.xml" />
    <import resource="classpath*:domain-context.xml" />

2/ Définir les tenants dispo dans les properties:
  tenants=tenant1,tenant2

  # Tenant par défaut
  default_db.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
  default_db.fr.bl.template.jdbc.driver.driverName=com.mysql.jdbc.Driver
  default_db.fr.bl.template.jdbc.driver.url=jdbc:mysql://localhost:3306/springtemplate
  default_db.fr.bl.template.jdbc.username=root
  default_db.fr.bl.template.jdbc.password=root
  
  # Les autres tenants
  tenant1.hibernate.dialect=org.hibernate.dialect.MySQL5Dialect
  tenant1.fr.bl.template.jdbc.driver.driverName=com.mysql.jdbc.Driver
  tenant1.fr.bl.template.jdbc.driver.url=jdbc:mysql://localhost:3306/client1
  tenant1.fr.bl.template.jdbc.username=root
  tenant1.fr.bl.template.jdbc.password=root
  ...
  
  