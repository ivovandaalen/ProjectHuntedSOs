var mongoose = require('mongoose');

console.log('Initializing jail schema');

var jailSchema = new mongoose.Schema({
    location: {
		latitude: { type: Number },
		longitude: { type: Number }
	}
});

module.exports = Jail = mongoose.model('Jail', jailSchema);