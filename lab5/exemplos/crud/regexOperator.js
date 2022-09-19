db.movieDetails.find({ "awards.text": { $regex: /^Won\s.*/ } }, {_id:0, "awards.text":1}).pretty()

db.movieDetails.find({ "awards.text": { $regex: /^Won.*/ } }, { title: 1, "awards": 1, _id: 0}).pretty()
