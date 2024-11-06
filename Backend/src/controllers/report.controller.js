const Remission = require('../models/Remission');
const ExcelJS = require('exceljs');
const PDFDocument = require('pdfkit');

const generateMonthlyReport = async (productorId = null) => {
  const match = productorId ? { productorId } : {};
  
  const report = await Remission.aggregate([
    { $match: match },
    {
      $group: {
        _id: {
          year: { $year: "$fechaDespacho" },
          month: { $month: "$fechaDespacho" },
          producto: "$producto"
        },
        totalKilos: { $sum: "$totalKilos" }
      }
    },
    { $sort: { "_id.year": 1, "_id.month": 1 } }
  ]);

  return report;
};

exports.getMonthlyReport = async (req, res) => {
  try {
    const productorId = req.user.role === 'productor' ? req.user.userId : null;
    const report = await generateMonthlyReport(productorId);
    res.json(report);
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

exports.downloadExcelReport = async (req, res) => {
  try {
    const productorId = req.user.role === 'productor' ? req.user.userId : null;
    const report = await generateMonthlyReport(productorId);

    const workbook = new ExcelJS.Workbook();
    const worksheet = workbook.addWorksheet('Monthly Report');

    worksheet.columns = [
      { header: 'Year', key: 'year' },
      { header: 'Month', key: 'month' },
      { header: 'Product', key: 'producto' },
      { header: 'Total Kilos', key: 'totalKilos' }
    ];

    report.forEach(record => {
      worksheet.addRow({
        year: record._id.year,
        month: record._id.month,
        producto: record._id.producto,
        totalKilos: record.totalKilos
      });
    });

    res.setHeader('Content-Type', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
    res.setHeader('Content-Disposition', 'attachment; filename=report.xlsx');

    await workbook.xlsx.write(res);
    res.end();
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};

exports.downloadPdfReport = async (req, res) => {
  try {
    const productorId = req.user.role === 'productor' ? req.user.userId : null;
    const report = await generateMonthlyReport(productorId);

    const doc = new PDFDocument();
    res.setHeader('Content-Type', 'application/pdf');
    res.setHeader('Content-Disposition', 'attachment; filename=report.pdf');
    doc.pipe(res);

    doc.fontSize(16).text('Monthly Production Report', { align: 'center' });
    doc.moveDown();

    report.forEach(record => {
      doc.fontSize(12).text(
        `Year: ${record._id.year}, Month: ${record._id.month}, ` +
        `Product: ${record._id.producto}, Total Kilos: ${record.totalKilos}`
      );
      doc.moveDown(0.5);
    });

    doc.end();
  } catch (error) {
    res.status(500).json({ message: error.message });
  }
};