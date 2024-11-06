const { body } = require('express-validator');

exports.createFarmValidator = [
  body('nombre').trim().notEmpty().withMessage('Farm name is required'),
  body('hectareas').isFloat({ min: 0 }).withMessage('Hectares must be a positive number'),
  body('municipio').trim().notEmpty().withMessage('Municipality is required')
];