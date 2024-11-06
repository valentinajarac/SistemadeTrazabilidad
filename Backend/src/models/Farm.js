const mongoose = require('mongoose');

const farmSchema = new mongoose.Schema({
  nombre: {
    type: String,
    required: true
  },
  hectareas: {
    type: Number,
    required: true,
    min: 0
  },
  municipio: {
    type: String,
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

module.exports = mongoose.model('Farm', farmSchema);