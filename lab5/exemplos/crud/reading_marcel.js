// PART I

db.movieDetails.find({rated:"PG-13"}).pretty();
db.movieDetails.find({rated:"PG-13"}).count();
db.movieDetails.find({rated:"PG-13", year:2009}).count();
// Dot notation for accessing fields
db.movieDetails.find({"tomato.meter":100}).count();
db.movieDetails.find({"tomato.meter":100}).pretty();

// PART II

db.movieDetails.find({"writers":["Ethan Coen", "Joel Coen"]}).count();
db.movieDetails.find({"writers":["Ethan Coen", "Joel Coen"]}).pretty();
// Order matters
db.movieDetails.find({"writers":["Joel Coen", "Ethan Coen"]}).count();
// Arrays containing
db.movieDetails.find({"actors":"Jeff Bridges"}).pretty();
// Match arrays elements ocurring in a specific position
db.movieDetails.find({"actors.0":"Jeff Bridges"}).pretty();


// PART III
db.movieDetails.find({rated:"PG"}).pretty()

// PART IV

var c = db.movieDetails.find();
var doc = function() { return c.hasNext ? c.next() : null; }
c.objsLeftInBatch();
doc();
doc();
doc();
doc();
c.objsLeftInBatch();

// PART V
db.movieDetails.find({rated:"PG"} , {title:1}).pretty();
db.movieDetails.find({rated:"PG"} , {_id:0, title:1}).pretty();
db.movieDetails.find({rated:"PG"} , {_id:0, writers:0, actors:0}).pretty();
