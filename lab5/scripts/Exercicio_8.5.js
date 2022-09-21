db.zips.aggregate([
    {"$project": {"first_char": {"$substr": ["$city", 0, 1]}, 
                  "pop": 1, 
                  "city": 1, 
                  "cep": "$_id", 
                  "state": 1}},
    {"$match": {"first_char": new RegExp("B|D|O|G|N|M")}},
    {"$group": {"_id": null, 
                "pop": {"$sum": "$pop"}}} 
])