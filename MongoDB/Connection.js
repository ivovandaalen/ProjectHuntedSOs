const mongoose = require('mongoose');

const URI = "mongodb+srv://masteruser:AvansIsWack42@hunted-sos.bo07l.mongodb.net/myFirstDatabase?retryWrites=true&w=majority";

const connectDB = async()=>{
    await mongoose.connect(URI);
    console.log('db connected');
}

module.exports = connectDB;