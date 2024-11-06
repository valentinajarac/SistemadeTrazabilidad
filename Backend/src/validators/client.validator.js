const { body } = require('express-validator');

exports.clientValidator = [
  body('nit').isInt().withMessage('NIT must be a number'),
  body('nombre').trim().notEmpty().withMessage('Name is required'),
  body('floid').isInt().custom(value => {
    return value.toString().length === 4;
  }).withMessage('FLOID must be a 4-digit number'),
  body('direccion').trim().notEmpty().withMessage('Address is required'),
  body('telefono').trim().notEmpty().withMessage('Phone number is required'),
  body('email').isEmail().withMessage('Invalid email address')
];