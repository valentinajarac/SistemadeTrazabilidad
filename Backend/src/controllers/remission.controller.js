const Remission = require('../models/Remission');
const Crop = require('../models/Crop');

exports.createRemission = async (req, res) => {
  try {
    const crop = await Crop.findById(req.body.cultivoId);
    if (!crop) {
      return res.status(404).json({ message: 'Crop not found' });
    }

    const remission = new Remission({
      ...req.body,
      productorId: req.user.role === 'productor' ? req.user.userId : req.body.productorId,
      producto: crop.producto
    });
    
    await remission.save();
    res.status(201).json(remission);
  } catch (error) {
    res.status(400).json({ message: error.message });
  }
};

exports.getRemissions = async (req, res) => {
  try {
    const query = req.user.role === 'admin' ? {} : { productorId: req.user.userId };
    const remissions = await Remission.find(query)
      .populate('productorId', 'nombreCompleto')
      .populate('clienteId', 'nombre')
      .populate('fincaId', 'nombre')
      .populate('cultivoId', 'producto estado');
    res.json(remissions);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

exports.updateRemission = async (req, res) => {
  try {
    const query = { _id: req.params.id };
    if (req.user.role === 'productor') {
      query.productorId = req.user.userId;
    }
    
    const remission = await Remission.findOneAndUpdate(query, req.body, { new: true });
    if (!remission) return res.status(404).json({ message: 'Remission not found or unauthorized' });
    res.json(remission);
  } catch (error) {
    res.status(400).json({ message: error.message });
  }
};

exports.deleteRemission = async (req, res) => {
  try {
    const query = { _id: req.params.id };
    if (req.user.role === 'productor') {
      query.productorId = req.user.userId;
    }
    
    const remission = await Remission.findOneAndDelete(query);
    if (!remission) return res.status(404).json({ message: 'Remission not found or unauthorized' });
    res.json({ message: 'Remission deleted successfully' });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};