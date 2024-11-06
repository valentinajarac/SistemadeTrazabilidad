const { body } = require('express-validator');

exports.remissionValidator = [
  body('fechaDespacho').isISO8601().withMessage('Invalid date format'),
  body('clienteId').isMongoId().withMessage('Invalid client ID'),
  body('fincaId').isMongoId().withMessage('Invalid farm ID'),
  body('cultivoId').isMongoId().withMessage('Invalid crop ID'),
  body('canastillasEnviadas').isInt({ min: 1 }).withMessage('Number of baskets must be a positive integer'),
  body('kilosPromedioPorCanastilla').isFloat({ min: 0 }).withMessage('Average kilos per basket must be a positive number')
];