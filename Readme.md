# Weather Report App 🌦️

Cette application permet de soumettre et de consulter des rapports météo en fonction de la localisation géographique. Elle utilise une interface web simple pour interagir avec un serveur qui gère les données météorologiques.

## Fonctionnalités
- **Soumettre un rapport météo** : Enregistrer un rapport avec la latitude, la longitude, la condition météo et la température.
- **Consulter tous les rapports** : Visualiser tous les rapports météorologiques enregistrés dans une table.
- **Rechercher des rapports proches** : Effectuer une recherche de rapports en fonction de la latitude, la longitude et un rayon spécifié autour de cette position.

## Prérequis

Avant de commencer, assurez-vous que vous avez les éléments suivants installés sur votre machine :
- **Java 11+** (pour faire tourner le backend basé sur Spring Boot)
- **Maven** (pour la gestion des dépendances et la construction du projet)
- **Node.js et npm** (pour la gestion de l'interface utilisateur avec des outils comme Bootstrap et fetch API)

## Installation

### Backend (Spring)

1. Clonez le dépôt du projet.
    ```bash
    git clone https://github.com/Gbane26/weather-report.git
    cd weather-report
    ```

2. Installez les dépendances avec Maven :
    ```bash
    mvn install
    mvn clean package // Pour build
    ```

Le backend sera accessible sur `http://localhost:8080`.

### Frontend (Interface Web)

Le frontend est constitué d'un fichier HTML avec du JavaScript. Il n'y a pas de compilation nécessaire. Cependant, assurez-vous d'avoir une connexion Internet pour charger Bootstrap depuis le CDN.

1. Ouvrez le fichier `index.jsp` dans votre navigateur pour accéder à l'interface de l'application.

## Utilisation

1. **Soumettre un rapport météo** :  
   - Entrez la latitude, la longitude, la condition météo (ex. : "ensoleillé") et la température dans le formulaire sous "Soumettre un rapport météo".
   - Cliquez sur "Soumettre" pour enregistrer le rapport dans le système.

2. **Rechercher des rapports proches** :  
   - Entrez la latitude, la longitude et le rayon en kilomètres dans le formulaire "Rechercher des rapports météo proches".
   - Cliquez sur "Rechercher" pour obtenir les rapports situés dans le rayon spécifié.

3. **Visualiser tous les rapports** :  
   - Tous les rapports soumis sont affichés dans une table, avec les informations de latitude, longitude, condition météo, température et horodatage.

## API

### 1. **POST /weather-report/api/weather/report**
Soumet un nouveau rapport météo.

**Paramètres** :
- `latitude` : Latitude du rapport (type `Double`)
- `longitude` : Longitude du rapport (type `Double`)
- `condition` : Condition météorologique (type `String`)
- `temperature` : Température en °C (type `Double`)

**Exemple de requête** :
```json
{
    "latitude": 42.5,
    "longitude": -70.0,
    "condition": "Ensoleillé",
    "temperature": 25.5
}
```

### 2. GET /weather-report/api/weather/all
Récupère tous les rapports météo enregistrés.

Exemple de réponse :

```json
[
    {
        "latitude": 42.5,
        "longitude": -70.0,
        "condition": "Ensoleillé",
        "temperature": 25.5,
        "timestamp": "2025-04-18T00:00:00Z"
    },
    {
        "latitude": 43.5,
        "longitude": -71.0,
        "condition": "Pluvieux",
        "temperature": 18.0,
        "timestamp": "2025-04-18T01:00:00Z"
    }
]
```
### 3. GET /weather-report/api/weather/nearby
Récupère les rapports météo proches d'un point donné (latitude, longitude) dans un rayon spécifié.

**Paramètres** :

- lat : Latitude (type Double)

- lon : Longitude (type Double)

- radiusKm : Rayon en kilomètres (type Double)

Exemple de requête :

```bash
GET /weather-report/api/weather/nearby?lat=42.5&lon=-70.0&radiusKm=50
```
Exemple de réponse :

```json
[
    {
        "latitude": 42.7,
        "longitude": -70.2,
        "condition": "Nuageux",
        "temperature": 22.0,
        "timestamp": "2025-04-18T00:30:00Z"
    }
]
```

## Problèmes connus
- Les rapports météorologiques peuvent ne pas être actualisés immédiatement après la soumission si la page n'est pas rafraîchie.

- Le système de recherche peut ne pas toujours donner des résultats si les coordonnées sont mal spécifiées ou si le rayon est trop grand.
