// 1. Stations dans Villeurbanne
db.velov_geo.find({"properties.commune": "VILLEURBANNE"});
// 2. Stations pas dans Villeurbanne
db.velov_geo.find({"properties.commune": {$ne: "VILLEURBANNE"}});
// 3. Stations n'ayant pas de deuxième adresse
db.velov_geo.find({"properties.address2": ""});
// 4. Stations ayant une deuxième adresse
db.velov_geo.countDocuments({"properties.address2": {$ne: ""}});
// 5. Stations avec plus de 2 vélos dispos
db.velov_geo.find({"properties.available_bikes": {$gt: 2}});
// 6. Communes distinctes
db.velov_geo.distinct("properties.commune");
// 7. Communes distinctes triées
db.velov_geo.distinct("properties.commune").sort();
// 8. Stations du 9eme triés par nombre de vélos croissants
db.velov_geo.find(
    {"properties.commune": "Lyon 9 ème"}
).sort({"properties.available_bikes": 1})
// 9. Projection sur le nom, la commune et le nombre de vélos dispos
db.velov_geo.find(
    {"properties.commune": "Lyon 9 ème"},
    {"properties.name": 1, "properties.commune": 1, "properties.available_bikes": 1}
).sort({"properties.available_bikes": 1})
// 10. Affiche commune et nombre de stations par commune
db.velov_geo.aggregate([
    {$group: {_id: "$properties.commune", nb_stations: {$sum: 1}}}
]);
// 11. Trier par commune croissante
db.velov_geo.aggregate([
    {$group: {_id: "$properties.commune", nb_stations: {$sum: 1}}},
    {$sort: {_id: 1}}
]);
// 12. Trier par nombre de stations décroissantes
db.velov_geo.aggregate([
    {$group: {_id: "$properties.commune", nb_stations: {$sum: 1}}},
    {$sort: {nb_stations: -1}}
]);
// 13. Nombre de stations de Villeurbanne groupés par nombre de vélos disponibles
db.velov_geo.aggregate([
    {$match: {"properties.commune": "VILLEURBANNE"}},
    {$group: {_id: "$properties.available_bikes", nb_stations: {$sum: 1}}},
    {$sort: {_id: 1}}
])
// 14. Remplacer le nombre de stations par une liste de leurs noms
db.velov_geo.aggregate([
    {$match: {"properties.commune": "VILLEURBANNE"}},
    {$group: {_id: "$properties.available_bikes", noms: {
            $push: "$properties.name"
        }
    }},
    {$sort: {_id: 1}}
])
//15. Calculer le nombre moyen de vélos disponibles par commune
db.velov_geo.aggregate([
    {$group: {_id:{commune:"$properties.commune"}, moyenne : {
            $avg : "$properties.available_bikes"
        }
    }},
])

// CREATION D'UN INDEX GEOMETRIQUE SUR COORDINATES
db.velov_geo.createIndex({ "geometry.coordinates" : "2dsphere" })
//16. Chercher les stations qui sont à moins de 500m du point donné
db.velov_geo.find({
    "geometry.coordinates":{
        $near: {
            $geometry: { type: "Point",  coordinates: [ 4.863132722360224, 45.77022676914935 ] },
            $minDistance: 0,
            $maxDistance: 500
        }
    }
})
//17. Listez les 5 stations les plus proches du point donné
db.velov_geo.aggregate([{
    $geoNear: {
        near: { type: "Point",  coordinates: [ 4.863132722360224, 45.77022676914935 ]},
        distanceField: "geometry",
        num: 5,
        spherical: true
    }
}]);

// CREATION D'UN INDEX TEXTUEL SUR POLE
db.velov_geo.createIndex( { "properties.pole": "text" } , { default_language: "french" })
//18. Cherchez les stations dont le pole répond à la requête "quartiers"
db.velov_geo.find(
    {$text: {$search: "quartiers"}},
    {"properties.pole": 1}
)