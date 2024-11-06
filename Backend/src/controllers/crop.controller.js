const Crop = require('../models/Crop');

exports.createCrop = async (req, res) => {
  try {
    const crop = new Crop({
      ...req.body,
      productorId: req.user.role === 'productor' ? req.user.userId : req.body.productorId
    });
    await crop.save();
    res.status(201).json(crop);
  } catch (error) {
    res.status(400).json({ message: error.message });
  }
};

exports.getCrops = async (req, res) => {
  try {
    const query = req.user.role === 'admin' ? {} : { productorId: req.user.userId };
    const crops = await Crop.find(query).populate('productorId', 'nombreCompleto');
    res.json(crops);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

exports.updateCrop = async (req, res) => {
  try {
    const query = { _id: req.params.id };
    if (req.user.role === 'productor') {
      query.productorId = req.user.userId;
    }
    
    const crop = await Crop.findOneAndUpdate(query, req.body, { new: true });
    if (!crop) return res.status(404).json({ message: 'Crop not found or unauthorized' });
    res.json(crop);
  } catch (error) {
    res.status(400).json({ message: error.message });
  }
};

exports.deleteCrop = async (req, res) => {
  try {
    const query = { _id: req.params.id };
    if (req.user.role === 'productor') {
      query.productorId = req.user.userId;
    }
    
    const crop = await Crop.findOneAndDelete(query);
    if (!crop) return res.status(404).json({ message: 'Crop not found or unauthorized' });
    res.json({ message: 'Crop deleted successfully' });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};