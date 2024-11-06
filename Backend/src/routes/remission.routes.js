const express = require('express');
const router = express.Router();
const remissionController = require('../controllers/remission.controller');
const { auth } = require('../middleware/auth');
const { remissionValidator } = require('../validators/remission.validator');
const validate = require('../middleware/validate');

router.post('/', auth, remissionValidator, validate, remissionController.createRemission);
router.get('/', auth, remissionController.getRemissions);
router.put('/:id', auth, remissionValidator, validate, remissionController.updateRemission);
router.delete('/:id', auth, remissionController.deleteRemission);

module.exports = router;