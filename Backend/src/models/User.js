const mongoose = require('mongoose');
const bcrypt = require('bcryptjs');

const userSchema = new mongoose.Schema({
  cedula: {
    type: Number,
    required: true,
    unique: true
  },
  nombreCompleto: {
    type: String,
    required: true
  },
  codigoTrazabilidad: {
    type: Number,
    required: true,
    unique: true
  },
  municipio: {
    type: String,
    required: true
  },
  telefono: {
    type: String,
    required: true
  },
  usuario: {
    type: String,
    required: true,
    unique: true
  },
  password: {
    type: String,
    required: true
  },
  role: {
    type: String,
    enum: ['admin', 'productor'],
    default: 'productor'
  }
}, {
  timestamps: true
});

userSchema.pre('save', async function(next) {
  if (!this.isModified('password')) return next();
  this.password = await bcrypt.hash(this.password, 10);
  next();
});

userSchema.methods.comparePassword = async function(candidatePassword) {
  return bcrypt.compare(candidatePassword, this.password);
};

module.exports = mongoose.model('User', userSchema);