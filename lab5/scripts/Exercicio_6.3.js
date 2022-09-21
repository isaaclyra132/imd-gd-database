db.small_zips.aggregate([
    {"$match": {"state": {"$in": ["CA","NY"]}}}, 
    {"$group": {"_id": {"state": "$state", 
                        "city": "$city"},
                "pop": {"$sum": "$pop"}}}, 
    {"$match": {"pop": {"$gt": 25000}}},
    {"$group": {"_id": null, "pop": {"$avg": "$pop"}}},
    { $project: { roundedPopValue: { $round: [ "$pop", 0 ] } } }
]);