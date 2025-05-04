<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Weather Report</title>
    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h1 class="text-primary mb-4">🌦️ Weather Report App</h1>

    <!-- Soumettre un rapport -->
    <div class="card mb-4">
        <div class="card-header bg-primary text-white">Soumettre un rapport météo</div>
        <div class="card-body">
            <form id="weatherForm">
                <div class="mb-3">
                    <label for="latitude" class="form-label">Latitude</label>
                    <input type="text" class="form-control" id="latitude" required>
                </div>
                <div class="mb-3">
                    <label for="longitude" class="form-label">Longitude</label>
                    <input type="text" class="form-control" id="longitude" required>
                </div>
                <div class="mb-3">
                    <label for="condition" class="form-label">Condition</label>
                    <input type="text" class="form-control" id="condition" required>
                </div>
                <div class="mb-3">
                    <label for="temperature" class="form-label">Température (°C)</label>
                    <input type="number" class="form-control" id="temperature" required>
                </div>
                <button type="submit" class="btn btn-primary">Soumettre</button>
            </form>
        </div>
    </div>

    <!-- Rechercher des rapports proches -->
    <div class="card mb-4">
        <div class="card-header bg-secondary text-white">🔍 Rechercher des rapports météo proches</div>
        <div class="card-body">
            <form id="nearbyForm" class="row g-3">
                <div class="col-md-4">
                    <label for="latSearch" class="form-label">Latitude</label>
                    <input type="text" class="form-control" id="latSearch" required>
                </div>
                <div class="col-md-4">
                    <label for="lngSearch" class="form-label">Longitude</label>
                    <input type="text" class="form-control" id="lngSearch" required>
                </div>
                <div class="col-md-4">
                    <label for="radiusSearch" class="form-label">Rayon (km)</label>
                    <input type="number" class="form-control" id="radiusSearch" required>
                </div>
                <div class="col-12 text-end">
                    <button type="submit" class="btn btn-secondary">Rechercher</button>
                    <button type="button" class="btn btn-outline-secondary ms-2" onclick="resetForm()">Réinitialiser</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Tableau des rapports -->
    <div class="card">
        <div class="card-header bg-info text-white">📋 Tous les rapports météo</div>
        <div class="card-body p-0">
            <table class="table table-striped mb-0">
                <thead class="table-light">
                    <tr>
                        <th>Latitude</th>
                        <th>Longitude</th>
                        <th>Condition</th>
                        <th>Température</th>
                        <th>Horodatage</th>
                    </tr>
                </thead>
                <tbody id="reportTableBody"></tbody>
            </table>
        </div>
    </div>
</div>

<script>
    const weatherForm = document.getElementById("weatherForm");
    const nearbyForm = document.getElementById("nearbyForm");
    const reportTableBody = document.getElementById("reportTableBody");

    // Soumettre un rapport météo
    weatherForm.addEventListener("submit", function (e) {
        e.preventDefault();

        const data = {
            latitude: document.getElementById("latitude").value,
            longitude: document.getElementById("longitude").value,
            condition: document.getElementById("condition").value,
            temperature: document.getElementById("temperature").value
        };

        fetch('/weather-report/api/weather/report', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        })
        .then(res => {
            if (res.ok) {
                alert("✅ Rapport météo enregistré !");
                weatherForm.reset();
                loadReports();
            } else {
                alert("❌ Erreur lors de l'enregistrement !");
            }
        });
    });

    // Charger tous les rapports météo
    function loadReports() {
        fetch('/weather-report/api/weather/all')
            .then(res => res.json())
            .then(data => populateTable(data))
            .catch(() => alert("❌ Erreur lors du chargement des rapports."));
    }

    // Recherche des rapports météo proches
    nearbyForm.addEventListener("submit", function (e) {
        e.preventDefault();

        const lat = parseFloat(document.getElementById("latSearch").value.trim());
        const lng = parseFloat(document.getElementById("lngSearch").value.trim());
        const radius = parseFloat(document.getElementById("radiusSearch").value.trim());

        if (isNaN(lat) || isNaN(lng) || isNaN(radius)) {
            alert("⚠️ Veuillez entrer des nombres valides !");
            return;
        }

        fetch(`/weather-report/api/weather/nearby?lat=`+lat+`&lon=`+lng+`&radiusKm=`+radius)
            .then(res => res.json())
            .then(data => populateTable(data))
            .catch((error) => alert("❌ Impossible de récupérer les rapports proches.", error));
    });

    // Affichage dans la table
    function populateTable(data) {
        console.log('Les données retournés sont vides', data)
        reportTableBody.innerHTML = data.length === 0 
            ? '<tr><td colspan="5" class="text-center text-muted">Aucun rapport trouvé</td></tr>'
            : data.map(report => `
                <tr>
                    <td>`+report.latitude+`</td> 
                    <td>`+report.longitude+`</td>
                    <td>`+report.condition+`</td>
                    <td>`+report.temperature+`</td>
                    <td>`+report.timestamp+`</td>
                </tr>
            `).join('');
    }

    // Réinitialiser le formulaire
    function resetForm() {
        nearbyForm.reset();
        loadReports();
    }

    // Charger les rapports au démarrage
    window.onload = loadReports;
</script>

</body>
</html>
