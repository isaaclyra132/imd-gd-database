use video;

// This first command will create the moviesScratch collection
db.moviesScratch.insertOne({ "title": "Rocky", "year": "1976", "imdb": "tt0075148"})

db.moviesScratch.find().pretty()
db.moviesScratch.insertOne({ "_id": "tt0075148", "title": "Rocky", "year": "1976" })
db.moviesScratch.find().pretty()
