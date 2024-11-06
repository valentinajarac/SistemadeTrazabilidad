const { body } = require('express-validator');

exports.cropValidator = [
  body('numeroPlants').isInt({ min: 1 }).withMessage('Number of plants must be a positive integer'),
  body('hectareas').isFloat({ min: 0 }).withMessage('Hectares must be a positive number'),
  body('producto').isIn(['uchuva', 'gulupa']).withMessage('Invalid product type'),
  body('estado').isIn(['produccion', 'vegetacion']).withMessage('Invalid state')
];