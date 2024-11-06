const mongoose = require('mongoose');

const remissionSchema = new mongoose.Schema({
  fechaDespacho: {
    type: Date,
    required: true
  },
  productorId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'User',
    required: true
  },
  clienteId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Client',
    required: true
  },
  fincaId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Farm',
    required: true
  },
  cultivoId: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Crop',
    required: true
  },
  canastillasEnviadas: {
    type: Number,
    required: true
  },
  producto: {
    type: String,
    enum: ['uchuva', 'gulupa'],
    required: true
  },
  kilosPromedioPorCanastilla: {
    type: Number,
    required: true
  },
  totalKilos: {
    type: Number,
    required: true
  }
}, {
  timestamps: true
});

remissionSchema.pre('save', function(next) {
  this.totalKilos = this.canastillasEnviadas * this.kilosPromedioPorCanastilla;
  next();
});

module.exports = mongoose.model('Remission', remissionSchema);