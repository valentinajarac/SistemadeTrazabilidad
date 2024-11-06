const Farm = require('../models/Farm');

exports.createFarm = async (req, res) => {
  try {
    const farm = new Farm({
      ...req.body,
      productorId: req.user.role === 'productor' ? req.user.userId : req.body.productorId
    });
    await farm.save();
    res.status(201).json(farm);
  } catch (error) {
    res.status(400).json({ message: error.message });
  }
};

exports.getFarms = async (req, res) => {
  try {
    const query = req.user.role === 'admin' ? {} : { productorId: req.user.userId };
    const farms = await Farm.find(query).populate('productorId', 'nombreCompleto');
    res.json(farms);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

exports.updateFarm = async (req, res) => {
  try {
    const query = { _id: req.params.id };
    if (req.user.role === 'productor') {
      query.productorId = req.user.userId;
    }
    
    const farm = await Farm.findOneAndUpdate(query, req.body, { new: true });
    if (!farm) return res.status(404).json({ message: 'Farm not found or unauthorized' });
    res.json(farm);
  } catch (error) {
    res.status(400).json({ message: error.message });
  }
};

exports.deleteFarm = async (req, res) => {
  try {
    const query = { _id: req.params.id };
    if (req.user.role === 'productor') {
      query.productorId = req.user.userId;
    }
    
    const farm = await Farm.findOneAndDelete(query);
    if (!farm) return res.status(404).json({ message: 'Farm not found or unauthorized' });
    res.json({ message: 'Farm deleted successfully' });
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};