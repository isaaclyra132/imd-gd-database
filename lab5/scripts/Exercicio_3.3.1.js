db.grades_flat.aggregate([
    {$match :{"score": {$gt: 65}}}, 
    {"$sort":{"score":+1}}, 
    {"$limit":1}, 
    {"$project":{"_id":0,"student_id":1}} 
])