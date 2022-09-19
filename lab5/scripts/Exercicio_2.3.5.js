db.movieDetails.find({
        "writers": {$in: ["Ethan Coen", "Joel Coen"]}
    }).count()