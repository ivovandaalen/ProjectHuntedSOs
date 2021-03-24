var express = require('express');
var app = express();

var routes = require('./routes.js');

//both index.js and things.js should be in same directory
app.use(express.json())
app.use('/', routes);

app.listen(process.env.PORT || 3000);