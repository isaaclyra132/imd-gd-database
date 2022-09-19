//////////////
// PART I

use school
db.students.find({student_id:5}).pretty()
// Takes some seconds

db.students.explain(true).find({student_id:5})
// Show the use of stage COLSCAN and docsExamined is 10000000

//////////////
// PART II

db.students.createIndex({student_id:1})
// You can watch progress in the log do mongod em C:\Program Files\MongoDB\Server\4.4\log

db.students.find({student_id:5}).pretty()
// Faster

db.students.explain(true).find({student_id:5})
// Show the use of stage IXSCAN and docsExamined is 10

db.students.createIndex({student_id:1, class_id:-1})

//////////////
// PART III

use school
db.students.getIndexes()

db.students.explain(true).find({student_id:5})
// Show winningPlan e rejectedPlans

//////////////
// PART IV

db.foo.insert({a:1, b:2})
db.foo.find();
db.foo.createIndex({a:1, b:1})
db.foo.explain().find({a:1, b:1})
// Mostre IsMultiKey false

db.foo.insert({a:3, b:[3,5,7]})
db.foo.explain().find({a:1, b:1})
// Mostre IsMultiKey true

db.foo.find({a:1, b:1}) 
// Não retorna documentos

db.foo.find({a:1, b:5}) 
// Não retorna documentos

db.foo.find({a:3, b:5}) 
// Retorna um documento

db.foo.insert({a:[3,4,6], b:[7,8,9]})
// Erro pois teríamos mais de um índice multikey

db.foo.insert({a:[3,4,6], b:8})
// OK pois a restrição é por documento

// Apague a base foo

//////////////
// PART V

use school
db.students.findOne()

// Maybe this is already created (check)
db.students.createIndex({'scores.score':1})
// Takes a long time

// Podemos agora procurar documentos com algum score acima de um certo valor
db.students.find({'scores.score':{'$gt':99}}).pretty()

db.students.explain().find({'scores.score':{'$gt':99}})
// Mostre indexBounds

//////////////
// PART VI

db.students.find({$and:[{'scores.type':'exam'},{'scores.score':{'$gt':99.8}}]}).pretty()
db.students.find({$and:[{'scores.type':'exam'},{'scores.score':{'$gt':99.8}}]}).count()

db.students.find({'scores':{'$elemMatch':{type:'exam', score:{'$gt':99.8}}}}).pretty()
db.students.find({'scores':{'$elemMatch':{type:'exam', score:{'$gt':99.8}}}}).count()

db.students.explain(true).find({'scores':{'$elemMatch':{type:'exam', score:{'$gt':99.8}}}})
// Mostre docsExamined e nReturned  

db.students.find({'scores.score':{'$gt':99.8}}).count()

db.students.dropIndex({'scores.score':1})
db.students.getIndexes()

//////////////
// PART VII

db.stuff.drop()
db.stuff.insert({'thing':'apple'})
db.stuff.insert({'thing':'pear'})
db.stuff.insert({'thing':'apple'}) 
db.stuff.createIndex({thing:1})
// Tudo OK

// Criando índice com a opção unique
db.stuff.dropIndex({thing:1})
db.stuff.createIndex({thing:1},{unique:true})
// Deve dar um erro duplicate key

// Removendo a apple duplicada
db.stuff.remove({'thing':'apple'}, {justOne:true})
db.stuff.createIndex({thing:1},{unique:true})

// Tentantod inserir duplicata
db.stuff.insert({'thing':'apple'})

// Apresentando os índice
db.stuff.getIndexes()
// Note que o índice padrão em _id não explicitamente dito ser único, mas é


//////////////
// PART VIII

use school
db.students.dropIndex({'scores.score':1})
db.students.getIndexes()
db.students.createIndex({'scores.score':1})
// OPEN A NEW SHELL AND CONNECT
use school
db.students.findOne()
// THE SECOND ONE WILL BE BLOCKED BY THE FIRST ONE

// KILL BOTH SHELLS

use school
db.students.dropIndex({'scores.score':1})
db.students.createIndex({'scores.score':1},{background:true})
// OPEN A NEW SHELL AND CONNECT
use school
db.students.findOne()
// THE SECOND ONE WILL BE NOT BLOCKED BY THE FIRST ONE

// KILL BOTH SHELLS

use school
db.students.dropIndex({'scores.score':1})
db.students.getIndexes()

//////////////
// PART IX

db.students.stats()


//////////////
// PART X

use school
var exp = db.students.explain()
exp.help();

exp.find({student_id:0, class_id:485}).sort({class_id:-1});
exp.find({student_id:0, class_id:485}).sort({class_id:-1}).count();

// This also works
db.students.find({student_id:0, class_id:485}).sort({class_id:-1}).explain();

//////////////
// PART XI

use school
var exp = db.students.explain("executionStats")
exp.find({student_id:0, class_id:485}).sort({class_id:-1});

db.students.createIndex({'class_id':1})
db.students.getIndexes()
var exp = db.students.explain("allPlansExecution")
exp.find({student_id:0, class_id:485}).sort({class_id:-1});

//////////////
// PART XII

use school
var exp = db.students.explain("executionStats")
exp.find({student_id:0});
// Verifique que totalDocsExamined = 10

db.students.dropIndex({student_id:1})
db.students.dropIndex({class_id:1})
db.students.dropIndex({student_id:1, class_id:-1})
db.students.dropIndex({'scores.score':1})
db.students.getIndexes()

//////////////
// PART XIII

use tourinfo

// Procurando pela string inteira
db.restaurants.find({name:'Morris Park Bake Shop'},{_id:0, name:1})

// Criando o índice
db.restaurants.ensureIndex({'name':'text'})
db.restaurants.getIndexes()

// Procurando por palavras
db.restaurants.find({$text:{$search:'Bake'}},{_id:0, name:1})
db.restaurants.find({$text:{$search:'Bake Shop'}},{_id:0, name:1})

// Ordenando por nível de match
db.restaurants.find({$text:{$search:'Bake Shop'}},{score:{$meta:'textScore'},_id:0, name:1}).sort({score:{$meta:'textScore'}})

db.restaurants.dropIndex("name_text")

//////////////
// PART XIV

use school

db.students.find({student_id:{$gt:500000}, class_id: 54}).sort({class_id:1}).explain("executionStats")
// Exibir executionStats (nReturned = 9906, totalKeysExamined = 0, totalDocsExamined = 10100000)

// Criando o Índice e verificando a eficiência dele nesta busca
db.students.createIndex({student_id:1})
db.students.find({student_id:{$gt:500000}, class_id: 54}).sort({class_id:1}).explain("executionStats")
// Exibir executionStats (nReturned = 9906, totalKeysExamined = 4999990, totalDocsExamined = 4999990)

// O sort foi feito em memória
// Exibir winningPlan stage:SORT e inputStage usando o índice IXSCAN

// Ainda podemos melhorar para termos totalDocsExamined próximo a 9906
db.students.createIndex({class_id:1})
db.students.find({student_id:{$gt:500000}, class_id: 54}).sort({student_id:1}).explain("executionStats")
// Exibir executionStats (nReturned = 9906, totalKeysExamined = 20149, totalDocsExamined = 20149)

// Ainda podemos melhorar para termos totalDocsExamined próximo a 9906
db.students.createIndex({student_id:1, class_id:1})
db.students.find({student_id:{$gt:500000}, class_id: 54}).sort({student_id:1}).explain("executionStats")
// Exibir executionStats (nReturned = 9906, totalKeysExamined = 850058, totalDocsExamined = 9906)

// Ainda podemos melhorar para termos totalKeysExamined próximo a 9906
db.students.createIndex({class_id:1, student_id:1})
db.students.find({student_id:{$gt:500000}, class_id: 54}).sort({student_id:1}).explain("executionStats")
// Exibir executionStats (nReturned = 9906, totalKeysExamined = 9906, totalDocsExamined = 9906)

// Analisando o o executionStats, podemos ver que os outros índices são agora inúteis para esta busca
db.students.dropIndex({student_id:1})
db.students.dropIndex({class_id:1})
db.students.dropIndex({student_id:1, class_id:1})
db.students.getIndexes()
db.students.find({student_id:{$gt:500000}, class_id: 54}).sort({student_id:1}).explain("executionStats")
// Exibir executionStats (nReturned = 9906, totalKeysExamined = 9906, totalDocsExamined = 9906)

db.students.dropIndex({class_id:1, student_id:1})

//////////////
// PART XV

db.students.dropIndex({student_id:1})
use school;
db.students.find({student_id:10000})
// Após a consulta, apresente o log do mongod em C:\Program Files\MongoDB\Server\4.4\log

//////////////
// PART XVI

-- https://docs.mongodb.com/manual/reference/method/db.setProfilingLevel/#set-profiling-level-options-slowms

use school;
db.setProfilingLevel(2, 3); // Level = 2, slowms = 3
db.students.find({student_id:10000});
db.system.profile.find().pretty();

// We can query on this document
db.system.profile.find({ns:/school.students/}).sort({ts:1}).pretty()
db.system.profile.find({millis:{$gt:1}}).sort({ts:1}).pretty()

// You can turn on this profiler from the shell, and set its parameters
db.getProfilingLevel()
db.getProfilingStatus()

db.setProfilingLevel(1, 100)
db.getProfilingStatus()
db.getProfilingLevel()

