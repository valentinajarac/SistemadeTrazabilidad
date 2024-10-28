import React, { useState, useEffect } from 'react';
import { Users, Truck, FileSpreadsheet, FileText } from 'lucide-react';
import { Bar } from 'react-chartjs-2';
import { Chart as ChartJS, CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend } from 'chart.js';
import { getAllShipmentsWithProducers } from '../services/api';
import { exportToExcel, exportToPDF } from '../utils/exportUtils';
import toast from 'react-hot-toast';

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

const AdminDashboard: React.FC = () => {
  const [totalUsers, setTotalUsers] = useState(0);
  const [totalShipments, setTotalShipments] = useState(0);
  const [shipments, setShipments] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      setLoading(true);
      const shipmentsData = await getAllShipmentsWithProducers();
      setShipments(shipmentsData);
      setTotalShipments(shipmentsData.length);
      // Get unique producers count
      const uniqueProducers = new Set(shipmentsData.map((s: any) => s.producerId));
      setTotalUsers(uniqueProducers.size);
    } catch (error) {
      toast.error('Error loading dashboard data');
    } finally {
      setLoading(false);
    }
  };

  const handleExportExcel = () => {
    try {
      exportToExcel(shipments, 'all-shipments-report');
      toast.success('Excel report downloaded successfully');
    } catch (error) {
      toast.error('Error generating Excel report');
    }
  };

  const handleExportPDF = () => {
    try {
      exportToPDF(shipments, 'all-shipments-report');
      toast.success('PDF report downloaded successfully');
    } catch (error) {
      toast.error('Error generating PDF report');
    }
  };

  // Mock data for the graph
  const monthlyData = {
    labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    datasets: [
      {
        label: 'Uchuva',
        data: [1200, 1900, 3000, 5000, 4000, 3000, 2000, 3000, 4000, 3000, 2000, 1000],
        backgroundColor: 'rgba(255, 206, 86, 0.5)',
        borderColor: 'rgba(255, 206, 86, 1)',
        borderWidth: 1
      },
      {
        label: 'Gulupa',
        data: [800, 1200, 2000, 3500, 3000, 2500, 1500, 2000, 3000, 2500, 1500, 800],
        backgroundColor: 'rgba(75, 192, 192, 0.5)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1
      }
    ]
  };

  const options = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top' as const,
      },
      title: {
        display: true,
        text: 'Monthly Fruit Production',
      },
    },
    scales: {
      x: {
        stacked: false,
      },
      y: {
        stacked: false,
        title: {
          display: true,
          text: 'Kilos'
        }
      }
    }
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Admin Dashboard</h1>
        <div className="space-x-4">
          <button
            onClick={handleExportExcel}
            disabled={loading}
            className="bg-green-600 text-white px-4 py-2 rounded-lg flex items-center gap-2 hover:bg-green-700 transition-colors"
          >
            <FileSpreadsheet className="w-5 h-5" />
            Export to Excel
          </button>
          <button
            onClick={handleExportPDF}
            disabled={loading}
            className="bg-red-600 text-white px-4 py-2 rounded-lg flex items-center gap-2 hover:bg-red-700 transition-colors"
          >
            <FileText className="w-5 h-5" />
            Export to PDF
          </button>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        <DashboardCard
          title="Total Users"
          value={totalUsers.toString()}
          icon={<Users className="w-8 h-8 text-blue-500" />}
        />
        <DashboardCard
          title="Total de despachos"
          value={totalShipments.toString()}
          icon={<Truck className="w-8 h-8 text-green-500" />}
        />
      </div>
      <div className="bg-white shadow rounded-lg p-6">
        <h2 className="text-xl font-semibold mb-4">Monthly Fruit Production</h2>
        <Bar options={options} data={monthlyData} />
      </div>
    </div>
  );
};

const DashboardCard: React.FC<{ title: string; value: string; icon: React.ReactNode }> = ({ title, value, icon }) => {
  return (
    <div className="bg-white shadow rounded-lg p-6">
      <div className="flex items-center justify-between">
        <div>
          <p className="text-gray-500 text-sm font-medium uppercase">{title}</p>
          <p className="mt-1 text-3xl font-semibold">{value}</p>
        </div>
        <div className="bg-gray-100 rounded-full p-3">
          {icon}
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;