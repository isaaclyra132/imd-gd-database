db.movieDetails.find({
        "genres": {$in: ["Comedy"]}
    }).count()