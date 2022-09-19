use zipcode
db.zips.aggregate([
    {$match:{state:"NY"}}
])


