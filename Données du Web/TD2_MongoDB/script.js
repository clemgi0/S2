// 1.
db.velov_geo.find({"properties.commune": "VILLEURBANNE"});
// 2.
db.velov_geo.find({"properties.commune": {$ne: "VILLEURBANNE"}});
// 3.
db.velov_geo.find({"properties.address2": ""});
// 4.
db.velov_geo.countDocuments({"properties.address2": {$ne: ""}});
// 5.
db.velov_geo.find({"properties.available_bikes": {$gt: 2}});
// 6.
db.velov_geo.distinct("properties.commune");
// 7.
db.velov_geo.distinct("properties.commune").sort();
// 8.
db.velov_geo.find(
    {"properties.commune": "Lyon 9 ème"}
).sort({"properties.available_bikes": 1})
// 9.
db.velov_geo.find(
    {"properties.commune": "Lyon 9 ème"},
    {"properties.name": 1, "properties.commune": 1, "properties.available_bikes": 1}
).sort({"properties.available_bikes": 1})
// 10.
db.velov_geo.aggregate([
    {$group: {_id: "$properties.commune", nb_stations: {$sum: 1}}}
]);
// 11.
db.velov_geo.aggregate([
    {$group: {_id: "$properties.commune", nb_stations: {$sum: 1}}},
    {$sort: {_id: 1}}
]);
// 12.
db.velov_geo.aggregate([
    {$group: {_id: "$properties.commune", nb_stations: {$sum: 1}}},
    {$sort: {nb_stations: -1}}
]);
// 13.
db.velov_geo.aggregate([
    {$match: {"properties.commune": "VILLEURBANNE"}},
    {$group: {_id: "$properties.available_bikes", nb_stations: {$sum: 1}}},
    {$sort: {_id: 1}}
])
// 14.
db.velov_geo.aggregate([
    {$match: {"properties.commune": "VILLEURBANNE"}},
    {$group: {_id: "$properties.available_bikes", noms: {
            $push: "$properties.name"
        }
    }},
    {$sort: {_id: 1}}
])
// 15.


// 16.
