db.movieDetails.find({ runtime: { $gt: 90 } }).count()
db.movieDetails.find({ runtime: { $gt: 90 } }).pretty()
db.movieDetails.find({ runtime: { $gt: 90 } } , {_id:0, title:1, runtime:1}).pretty()

db.movieDetails.find({ runtime: { $gte: 90, $lte: 120 } }).count()
db.movieDetails.find({ runtime: { $gte: 90, $lte: 120 } }).pretty()
db.movieDetails.find({ runtime: { $gte: 90, $lte: 120 } } , {_id:0, title:1, runtime:1}).pretty()

db.movieDetails.find({ "tomato.meter": { $gte: 95 }, runtime: { $gt: 180 } }, {_id:0, title:1, runtime:1})

db.movieDetails.find({ rated: { $ne: "UNRATED" } }, {_id:0, title:1, rated:1}).pretty()

db.movieDetails.find({ rated: { $in: ["G", "PG"] } }, {_id:0, title:1, rated:1}).pretty()

db.movieDetails.find({ rated: { $nin: ["G", "PG"] } }, {_id:0, title:1, rated:1}).pretty()

