const express = require('express');
const router = express.Router();
const farmController = require('../controllers/farm.controller');
const { auth, isAdmin } = require('../middleware/auth');
const { createFarmValidator } = require('../validators/farm.validator');
const validate = require('../middleware/validate');

router.post('/', auth, createFarmValidator, validate, farmController.createFarm);
router.get('/', auth, farmController.getFarms);
router.put('/:id', auth, createFarmValidator, validate, farmController.updateFarm);
router.delete('/:id', auth, farmController.deleteFarm);

module.exports = router;