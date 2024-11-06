const mongoose = require('mongoose');

const cropSchema = new mongoose.Schema({
  numeroPlants: {
    type: Number,
    required: true
  },
  hectareas: {
    type: Number,
    required: true
  },
  producto: {
    type: String,
    enum: ['uchuva', 'gulupa'],
    required: true
  },
  estado: {
    type: String,
    enum: ['produccion', 'vegetacion'],
    required: true
  },
  productorId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  }
}, {
  timestamps: true
});

module.exports = mongoose.model('Crop', cropSchema);