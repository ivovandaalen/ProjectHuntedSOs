var mongoose = require('mongoose');

console.log('Initializing player schema');

var playerSchema = new mongoose.Schema({
    name: { type: String, required: true },
    role: { type: String, required: true },
	arrested: { type: Boolean },
	location: {
		latitude: { type: Number },
		longitude: { type: Number }
	},
    loot: [{ type: mongoose.Schema.Types.ObjectId, ref: 'Loot' }]
}, {
    toJSON: { virtuals: true },
    toObject: { virtuals: true }
});

playerSchema.virtual('numberOfLoot').get(function () {
    return this.loot.length;
});

module.exports = Player = mongoose.model('Player', playerSchema);