use agg
db.products.aggregate([
    {$group:
     {
	 _id: {'maker':"$manufacturer"},
	 num_products:{$sum:1}
     }
    }
])


