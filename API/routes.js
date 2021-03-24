var express = require('express');
var fs = require('fs');
var router = express.Router();

var geolib = require('geolib');

var players = fs.readFileSync('players.json');
var objectPlayers = JSON.parse(players);

router.get('/players', function(req, res){
    res.send('' + JSON.stringify(objectPlayers, null, '\t'))
});

router.get('/distance/:id', function(req, res){
    var distances = [];
    var updateIndex = objectPlayers['players'].map(function(player){
        return player.id;
    }).indexOf(parseInt(req.params.id));

    var playerLoc = objectPlayers['players'][updateIndex].location;

    objectPlayers['players'].forEach(item => {
        if(item.id != req.params.id){
            var s = geolib.getPreciseDistance(
                { latitude: playerLoc.lat, longitude: playerLoc.long },
                { latitude: item.location.lat, longitude: item.location.long}
            );

            distances.push({
                'id':   item.id,
                'distance': s
            });
        }
    })

    function finished(err){
    }
    res.send(JSON.stringify(distances, null, 2))
 });

router.put('/location/:id', function(req, res){
    var updateIndex = objectPlayers['players'].map(function(player){
        return player.id;
     }).indexOf(parseInt(req.params.id));

    // var loc = JSON.parse(req.body.location)
    objectPlayers['players'][updateIndex].location.lat = req.body.location.lat;
    objectPlayers['players'][updateIndex].location.long = req.body.location.long;

    players = JSON.stringify(objectPlayers, null, 2);
    fs.writeFile('players.json', players, finished);

    function finished(err){
        console.log('sskkkrt');
    }
     res.send('index ' + updateIndex + ' is geupdate')
});

router.put('/name/:id', function(req, res){
    var updateIndex = objectPlayers['players'].map(function(player){
        return player.id;
     }).indexOf(parseInt(req.params.id));


    if(req.body.name != null){
        objectPlayers['players'][updateIndex].name = req.body.name;
    }

    players = JSON.stringify(objectPlayers, null, 2);

    fs.writeFile('players.json', players, finished);

    function finished(err){
    }

     res.send('' + updateIndex)
});

module.exports = router;