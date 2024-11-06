const { body } = require('express-validator');

exports.createUserValidator = [
  body('cedula').isInt().withMessage('Cedula must be a number'),
  body('nombreCompleto').trim().notEmpty().withMessage('Full name is required'),
  body('codigoTrazabilidad').isInt().withMessage('Traceability code must be a number'),
  body('municipio').trim().notEmpty().withMessage('Municipality is required'),
  body('telefono').trim().notEmpty().withMessage('Phone number is required'),
  body('usuario').trim().notEmpty().withMessage('Username is required'),
  body('password').isLength({ min: 6 }).withMessage('Password must be at least 6 characters long'),
  body('role').isIn(['admin', 'productor']).withMessage('Invalid role')
];