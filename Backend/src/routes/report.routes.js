const express = require('express');
const router = express.Router();
const reportController = require('../controllers/report.controller');
const { auth } = require('../middleware/auth');

router.get('/monthly', auth, reportController.getMonthlyReport);
router.get('/download/excel', auth, reportController.downloadExcelReport);
router.get('/download/pdf', auth, reportController.downloadPdfReport);

module.exports = router;