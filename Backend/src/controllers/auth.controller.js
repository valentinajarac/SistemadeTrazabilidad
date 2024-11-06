const User = require('../models/User');
const jwt = require('jsonwebtoken');

exports.login = async (req, res) => {
  try {
    const { usuario, password } = req.body;
    const user = await User.findOne({ usuario });
    
    if (!user || !(await user.comparePassword(password))) {
      return res.status(401).json({ message: 'Invalid credentials' });
    }

    const token = jwt.sign(
      { userId: user._id, role: user.role },
      process.env.JWT_SECRET,
      { expiresIn: '24h' }
    );

    res.json({ token, user: { 
      id: user._id,
      role: user.role,
      nombreCompleto: user.nombreCompleto
    }});
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};