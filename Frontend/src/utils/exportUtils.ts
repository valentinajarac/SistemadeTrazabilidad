import { utils, writeFile } from 'xlsx';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';

interface ShipmentData {
  dispatchDate: string;
  producerId?: string;
  producerName?: string;
  clientId: string;
  farmId: string;
  cropId: string;
  basketsSent: number;
  productSent: string;
  averageWeight: number;
  totalKilosSent: number;
}

export const exportToExcel = (data: ShipmentData[], fileName: string) => {
  const worksheet = utils.json_to_sheet(data.map(shipment => ({
    'Dispatch Date': shipment.dispatchDate,
    'Producer': shipment.producerName || 'N/A',
    'Product': shipment.productSent,
    'Baskets Sent': shipment.basketsSent,
    'Average Weight (kg)': shipment.averageWeight,
    'Total Kilos': shipment.totalKilosSent
  })));

  const workbook = utils.book_new();
  utils.book_append_sheet(workbook, worksheet, 'Shipments');
  writeFile(workbook, `${fileName}.xlsx`);
};

export const exportToPDF = (data: ShipmentData[], fileName: string) => {
  const doc = new jsPDF();

  const tableColumn = [
    'Dispatch Date', 
    'Producer', 
    'Product', 
    'Baskets', 
    'Avg Weight', 
    'Total Kilos'
  ];
  const tableRows = data.map(shipment => [
    shipment.dispatchDate,
    shipment.producerName || 'N/A',
    shipment.productSent,
    shipment.basketsSent,
    shipment.averageWeight,
    shipment.totalKilosSent
  ]);

  doc.text('Fruit Shipments Report', 14, 15);
  
  autoTable(doc, {
    head: [tableColumn],
    body: tableRows,
    startY: 20,
  });

  doc.save(`${fileName}.pdf`);
};