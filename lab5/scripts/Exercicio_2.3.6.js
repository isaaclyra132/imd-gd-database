db.movieDetails.find({
        "rated": {$in: ["PG-13", "R"]}
    }).count()