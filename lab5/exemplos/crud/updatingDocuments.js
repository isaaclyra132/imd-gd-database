db.movieDetails.find({title: "The Martian"}).pretty()
// The martian has no poster and no awards


db.movieDetails.updateOne(
    {title: "The Martian"}, 
    {$set: {
        poster: "http://ia.media-imdb.com/images/M/MV5BMTc2MTQ3MDA1Nl5BMl5BanBnXkFtZTgwODA3OTI4NjE@._V1_SX300.jpg"
    }
})

db.movieDetails.find({title: "The Martian"}).pretty()
// The martian now has the poster field


db.movieDetails.updateOne(
    {title: "The Martian"}, 
    {$set: {
        "awards": {
          "wins": 8,
          "nominations": 14,
          "text": "Nominated for 3 Golden Globes. Another 8 wins & 14 nominations."
        }
    }
});

db.movieDetails.find({title: "The Martian"}).pretty()
// The martian now has the awards field


/*
 * Updates are used to correct errors and, over time, keep our data current. 
 * For movie data, much of what's there is static: directors, authors and the 
 * like. Other content such as reviews and ratings will need to be updated as 
 * users take action. We could use $set for this purpose, but that's an error 
 * prone approach. It's too easy to do the arithmetic incorrectly. Instead, we 
 * have a number of operators that support numeric updates of data: 
 * $min, $max, $inc, $mul. Let's look at an example using $inc to update reviews.
 */

// Increments the number of tomato reviews by 3 and the number of useReviews by 25
db.movieDetails.find({title: "The Martian"},{_id:0, title:1, "tomato.reviews":1, "tomato.userReviews":1}).pretty()

db.movieDetails.updateOne(
  {title: "The Martian"}, 
  {
  $inc: {
    "tomato.reviews": 3,
    "tomato.userReviews": 25
  }
})

db.movieDetails.find({title: "The Martian"},{_id:0, title:1, "tomato.reviews":1, "tomato.userReviews":1}).pretty()

// $addToSet adds an element to an array if it does not already exists
// Check array updates operator

db.movieDetails.find({title: "The Martian"},{_id:0, title:1, reviews:1}).pretty()

var reviewText1 = [
  "The Martian could have been a sad drama film, instead it was a ",
  "hilarious film with a little bit of drama added to it. The Martian is what ",
  "everybody wants from a space adventure. Ridley Scott can still make great ",
  "movies and this is one of his best."
].join()
db.movieDetails.updateOne({
  title: "The Martian"
}, {
  $push: {
    reviews: {
      rating: 4.5,
      date: ISODate("2016-01-12T09:00:00Z"),
      reviewer: "Spencer H.",
      text: reviewText1
    }
  }
})

db.movieDetails.find({title: "The Martian"},{_id:0, title:1, reviews:1}).pretty()

var reviewText2 = [
  "i believe its ranked high due to its slogan 'Bring him Home' there is nothi",
  "ng in the movie, nothing at all ! Story telling for fiction story !"
].join()

var reviewText3 = [
  "This is a masterpiece. The ending is quite different from the book - the mo",
  "vie provides a resolution whilst a book doesn't."
].join()

var reviewText4 = [
  "There have been better movies made about space, and there are elements of t",
  "he film that are borderline amateur, such as weak dialogue, an uneven tone,",
  " and film cliches."
].join()

var reviewText5 = [
  "This novel-adaptation is humorous, intelligent and captivating in all its v",
  "isual-grandeur. The Martian highlights an impeccable Matt Damon, power-stac",
  "ked ensemble and Ridley Scott's masterful direction, which is back in full ",
  "form."
].join()

var reviewText6 = [
  "A declaration of love for the potato, science and the indestructible will t",
  "o survive. While it clearly is the Matt Damon show (and he is excellent), t",
  "he supporting cast may be among the strongest seen on film in the last 10 y",
  "ears. An engaging, exciting, funny and beautifully filmed adventure thrille",
  "r no one should miss."
].join()

var reviewText7 = [
  "The Martian could have been a sad drama film, instead it was a hilarious fi",
  "lm with a little bit of drama added to it. The Martian is what everybody wa",
  "nts from a space adventure. Ridley Scott can still make great movies and th",
  "is is one of his best."
].join()

// Using $each we will add each of the elements of the given array to the reviews array
// If we do not use $each, the entire given array will be added as a single element to the reviews array

db.movieDetails.updateOne({
  title: "The Martian"
}, {
  $push: {
    reviews: {
      $each: [{
        rating: 0.5,
        date: ISODate("2016-01-12T07:00:00Z"),
        reviewer: "Yabo A.",
        text: reviewText2
      }, {
        rating: 5,
        date: ISODate("2016-01-12T09:00:00Z"),
        reviewer: "Kristina Z.",
        text: reviewText3
      }, {
        rating: 2.5,
        date: ISODate("2015-10-26T04:00:00Z"),
        reviewer: "Matthew Samuel",
        text: reviewText4
      }, {
        rating: 4.5,
        date: ISODate("2015-12-13T03:00:00Z"),
        reviewer: "Eugene B",
        text: reviewText5
      }, {
        rating: 4.5,
        date: ISODate("2015-10-22T00:00:00Z"),
        reviewer: "Jens S",
        text: reviewText6
      }, {
        rating: 4.5,
        date: ISODate("2016-01-12T09:00:00Z"),
        reviewer: "Spencer H.",
        text: reviewText7
      }
      ]
    }
  }
})

db.movieDetails.find({title: "The Martian"},{_id:0, title:1, reviews:1}).pretty()

// Using $slice, we keep only the first 5 elements in the reviews array (-5 keeps the last 5)
// Notice that we have to use $position: 0 to add the new elements starting at position 0

db.movieDetails.updateOne({
  title: "The Martian"
}, {
  $push: {
    reviews: {
      $each: [{
        rating: 0.5,
        date: ISODate("2016-01-13T07:00:00Z"),
        reviewer: "Shannon B.",
        text: "Enjoyed watching with my kids!"
      }],
      $position: 0,
      $slice: 5
    }
  }
})

db.movieDetails.find({title: "The Martian"},{_id:0, title:1, reviews:1}).pretty()

// Let's use updateMany to do some cleaning

db.movieDetails.find({rated: null},{_id:0, title:1, rated:1}).pretty()
db.movieDetails.find({rated: null},{_id:0, title:1, rated:1}).count()

// Update many has a similar behaviour to updateOne but updates ALL the documents
// that match the filter criteria 

db.movieDetails.find({rated: "UNRATED"},{_id:0, title:1, rated:1}).count()

// Could do this, but it's probably the wrong semantics.
db.movieDetails.updateMany({
  rated: null
}, {
  $set: {
    rated: "UNRATED"
  }
})


// Better to do this.
db.movieDetails.updateMany({
  rated: null
}, {
  $unset: {
    rated: ""
  }
})

db.movieDetails.find({rated: null},{_id:0, title:1, rated:1}).count()
// Notice that the count does not change, but that is because
// this is also interpreted as document that do not have a rated field
// See that the fields are really gone
db.movieDetails.find({rated: null},{_id:0, title:1, rated:1}).pretty()


// UPSERTS - if no documents are found matching the filter, we INSERT a document

var detail = {
  "title": "The Martian",
  "year": 2015,
  "rated": "PG-13",
  "released": ISODate("2015-10-02T04:00:00Z"),
  "runtime": 144,
  "countries": [
    "USA",
    "UK"
  ],
  "genres": [
    "Adventure",
    "Drama",
    "Sci-Fi"
  ],
  "director": "Ridley Scott",
  "writers": [
    "Drew Goddard",
    "Andy Weir"
  ],
  "actors": [
    "Matt Damon",
    "Jessica Chastain",
    "Kristen Wiig",
    "Jeff Daniels"
  ],
  "plot": "During a manned mission to Mars, Astronaut Mark Watney is presumed" +
  " dead after a fierce storm and left behind by his crew. But Watney has" +
  " survived and finds himself stranded and alone on the hostile planet." +
  " With only meager supplies, he must draw upon his ingenuity, wit and " +
  "spirit to subsist and find a way to signal to Earth that he is alive.",
  "poster": "http://ia.media-imdb.com/images/M/" +
  "MV5BMTc2MTQ3MDA1Nl5BMl5BanBnXkFtZTgwODA3OTI4NjE@._V1_SX300.jpg",
  "imdb": {
    "id": "tt3659388",
    "rating": 8.2,
    "votes": 187881
  },
  "tomato": {
    "meter": 93,
    "image": "certified",
    "rating": 7.9,
    "reviews": 280,
    "fresh": 261,
    "consensus": "Smart, thrilling, and surprisingly funny, The Martian offers" +
    " a faithful adaptation of the bestselling book that brings out the best " +
    "in leading man Matt Damon and director Ridley Scott.",
    "userMeter": 92,
    "userRating": 4.3,
    "userReviews": 104999
  },
  "metacritic": 80,
  "awards": {
    "wins": 8,
    "nominations": 14,
    "text": "Nominated for 3 Golden Globes. Another 8 wins & 14 nominations."
  },
  "type": "movie"
};

detail

// The updateOne will guarantee that you will not introduce the same film again
// but with a different object id
// The upsert:true, however, guarantees that, if the film does not exist, then I will
// introduce it

db.movieDetails.updateOne({
  "imdb.id": detail.imdb.id
}, {
  $set: detail
}, {
  upsert: true
});

// REPLACE ONE

db.moviesScratch.find({}).pretty()

// What if we want to replace that data with more detailed information?

db.moviesScratch.insertOne({
  "title": "The Martian",
  "year": 2015,
  "imdb": "tt3659388",
  "type": "movie"
});

db.moviesScratch.find({title: "The Martian"}).pretty()

db.moviesScratch.replaceOne({
    "imdb": detail.imdb.id
  },
  detail);

db.moviesScratch.find({title: "The Martian"}).pretty()

db.moviesScratch.deleteMany({
  "title": "The Martian"
});
