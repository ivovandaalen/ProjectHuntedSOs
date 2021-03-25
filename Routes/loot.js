var express = require('express');
var Loot = require('../MongoDB/loot');
var router = express.Router();

router.post('/', function (req, res) {
  const name = req.body.name;
  let loot = {};
  loot.name = name;

  let lootModel = new Loot(loot);
  lootModel.save();

  res.json(lootModel);
});

router.get('/', function (req, res) {
  var loot = Loot.find({}, function (err, result) {
    if (!err) {
      return res.send(result);
    }
  });

});

module.exports = router;