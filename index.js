var express = require('express');
var app = express();
const connectDB = require('./MongoDB/Connection');

connectDB();
var loot = require('./Routes/loot.js');
var player = require('./Routes/player.js');
var jail = require('./Routes/jail.js');

app.use(express.json())
app.use('/loot', loot);
app.use('/player', player);
app.use('/jail', jail);

app.listen(process.env.PORT || 3000);