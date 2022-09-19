db.movieDetails.find({
        "rated": "PG-13", 
        "awards.wins": 0,
        "year": 2013, 
    }, 
    {
        "_id":0, 
        "title": 1
    })