const express = require('express');
const router = express.Router();
const cropController = require('../controllers/crop.controller');
const { auth } = require('../middleware/auth');
const { cropValidator } = require('../validators/crop.validator');
const validate = require('../middleware/validate');

router.post('/', auth, cropValidator, validate, cropController.createCrop);
router.get('/', auth, cropController.getCrops);
router.put('/:id', auth, cropValidator, validate, cropController.updateCrop);
router.delete('/:id', auth, cropController.deleteCrop);

module.exports = router;