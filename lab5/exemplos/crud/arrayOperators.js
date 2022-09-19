// All elements must be included in the genres array
db.movieDetails.find({ genres: { $all: ["Comedy", "Drama", "Crime"] } }).pretty()

// The country array have only 1 element
db.movieDetails.find({ countries: { $size: 1 } }).pretty()

// Suppose we have the following field in one of the films

db.moviesScratch.insertOne(
        {
	    "_id" : "tt0084123",
	    "title" : "Film Test",
	    "year" : 1982,
	    "type" : "movie",
        "boxOffice": [ { "country": "USA", "revenue": 41.3 },
                     { "country": "Australia", "revenue": 2.9 },
                     { "country": "UK", "revenue": 10.1 },
                     { "country": "Germany", "revenue": 4.3 },
                     { "country": "France", "revenue": 3.5 } ]
        }
);


// $elemMatch requires all criteria to be satisfied by each element of the array
db.moviesScratch.find({ boxOffice: {$elemMatch: { country: "UK", revenue: { $gt: 5 } } } }).pretty()



