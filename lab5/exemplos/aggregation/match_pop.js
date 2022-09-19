// Retornar zipcodes com população superior a 100000  

use zipcode
db.zips.aggregate([
    {$match:{pop:{$gt:100000}}} 
])


