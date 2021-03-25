var express = require('express');
var Jail = require('../MongoDB/jail');
var router = express.Router();

router.post('/', function (req, res) {
    const location = req.body.location;
    let jail = {};
    jail.location = location;

    let jailModel = new Jail(jail);
    jailModel.save();

    res.json(jailModel);
});

router.get('/', function (req, res) {
    var jail = Jail.find({}, function (err, result) {
        if (!err) {
            return res.send(result);
        }
    });
});

router.put('/:id', function (req, res) {
    var query = { _id: req.params.id };
    var newLoc = {
        location:
        {
            latitude: req.body.location.latitude,
            longitude: req.body.location.longitude
        }
    }
    Jail.updateOne(query, newLoc, function(err, result){
        function finished(err) {
            console.log(err)
        }
        res.send("Update gelukt");
    })
});

module.exports = router;