const express = require('express');
const router = express.Router();
const clientController = require('../controllers/client.controller');
const { auth, isAdmin } = require('../middleware/auth');
const { clientValidator } = require('../validators/client.validator');
const validate = require('../middleware/validate');

router.post('/', auth, isAdmin, clientValidator, validate, clientController.createClient);
router.get('/', auth, clientController.getClients);
router.put('/:id', auth, isAdmin, clientValidator, validate, clientController.updateClient);
router.delete('/:id', auth, isAdmin, clientController.deleteClient);

module.exports = router;