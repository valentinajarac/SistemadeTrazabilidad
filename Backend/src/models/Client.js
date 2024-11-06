const mongoose = require('mongoose');

const clientSchema = new mongoose.Schema({
  nit: {
    type: Number,
    required: true,
    unique: true
  },
  nombre: {
    type: String,
    required: true
  },
  floid: {
    type: Number,
    required: true,
    validate: {
      validator: function(v) {
        return v.toString().length === 4;
      },
      message: 'FLOID must be a 4-digit number'
    }
  },
  direccion: {
    type: String,
    required: true
  },
  telefono: {
    type: String,
    required: true
  },
  email: {
    type: String,
    required: true,
    unique: true
  }
}, {
  timestamps: true
});

module.exports = mongoose.model('Client', clientSchema);