var mongoose = require('mongoose');

console.log('Initializing loot schema');

var lootSchema = new mongoose.Schema({
    name: { type: String, required: true }
});

module.exports = Loot = mongoose.model('loot', lootSchema);