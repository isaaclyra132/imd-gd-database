db.movieDetails.find({
        "genres": {$in: ["Family"]}
    }).count()