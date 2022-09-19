db.movieDetails.find({
        "genres.1": "Western"
    }).count()
