db.movieDetails.find({
        "rated": "PG-13", 
        "awards.nominations": 10
    }).count()