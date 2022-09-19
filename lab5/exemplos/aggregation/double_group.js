use agg

// This does not work because the score is an array
// Average becomes null
db.grades.aggregate([
    {'$group':{_id:{class_id:"$class_id", student_id:"$student_id"}, 
               'average':{"$avg":"$score"}}},
    {'$group':{_id:"$_id.class_id", 'average':{"$avg":"$average"}}}
    
    ])
    
// Let us first unwind the scores array and project the needed fields  

db.grades.aggregate([
    {$unwind:"$scores"},
    
    {
        $project:
        {
            class_id:"$class_id",
            student_id:"$student_id",
            type:"$scores.type",
            score: "$scores.score"
        }
    }
]).pretty()

// Now we can group it

db.grades.aggregate([
    {$unwind:"$scores"},
    
    {
        $project:
        {
            class_id:"$class_id",
            student_id:"$student_id",
            score: "$scores.score"
        }
    },

    {'$group':{_id:{class_id:"$class_id", 
                    student_id:"$student_id"}, 
               'average':{"$avg":"$score"}}},    
]).pretty()

// Finally, we can get the average of each class

db.grades.aggregate([
    {$unwind:"$scores"},
    
    {
        $project:
        {
            class_id:"$class_id",
            student_id:"$student_id",
            score: "$scores.score"
        }
    },

    {'$group':{_id:{class_id:"$class_id", 
                    student_id:"$student_id"}, 
               'average':{"$avg":"$score"}}},
               
    {'$group':{_id:"$_id.class_id", 'average':{"$avg":"$average"}}}
]).pretty()





    

