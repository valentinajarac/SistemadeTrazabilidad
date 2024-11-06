const express = require('express');
const router = express.Router();
const userController = require('../controllers/user.controller');
const { auth, isAdmin } = require('../middleware/auth');
const { createUserValidator } = require('../validators/user.validator');
const validate = require('../middleware/validate');

router.post('/', auth, isAdmin, createUserValidator, validate, userController.createUser);
router.put('/:id', auth, isAdmin, createUserValidator, validate, userController.updateUser);
router.delete('/:id', auth, isAdmin, userController.deleteUser);

module.exports = router;