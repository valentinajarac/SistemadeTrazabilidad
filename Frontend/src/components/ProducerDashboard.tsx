import React, { useState, useEffect } from 'react';
import { Apple, Truck, Tractor, Sprout, FileSpreadsheet, FileText } from 'lucide-react';
import { Line } from 'react-chartjs-2';
import { useAuth } from '../contexts/AuthContext';
import { getProducerShipments, getFarms, getCrops } from '../services/api';
import { exportToExcel, exportToPDF } from '../utils/exportUtils';
import toast from 'react-hot-toast';
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
} from 'chart.js';

ChartJS.register(
  CategoryScale,
  LinearScale,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend
);

interface ShipmentData {
  dispatchDate: string;
  productSent: 'uchuva' | 'gulupa';
  totalKilosSent: number;
}

interface FarmData {
  id: string;
  farmName: string;
  hectares: string;
  municipality: string;
}

interface CropData {
  id: string;
  plantCount: string;
  hectares: string;
  product: 'uchuva' | 'gulupa';
  status: 'produccion' | 'vegetacion';
  farmId: string;
}

const ProducerDashboard: React.FC = () => {
  const { user } = useAuth();
  const [shipments, setShipments] = useState<ShipmentData[]>([]);
  const [farms, setFarms] = useState<FarmData[]>([]);
  const [crops, setCrops] = useState<CropData[]>([]);
  const [totalUchuva, setTotalUchuva] = useState(0);
  const [totalGulupa, setTotalGulupa] = useState(0);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (user) {
      fetchData();
    }
  }, [user]);

  const fetchData = async () => {
    try {
      setLoading(true);
      const [shipmentsData, farmsData, cropsData] = await Promise.all([
        getProducerShipments(user!.id),
        getFarms(),
        getCrops()
      ]);

      setShipments(shipmentsData);
      setFarms(farmsData);
      setCrops(cropsData);

      // Calculate totals
      const uchuvaTotal = shipmentsData
        .filter((s: ShipmentData) => s.productSent === 'uchuva')
        .reduce((acc: number, curr: ShipmentData) => acc + curr.totalKilosSent, 0);
      
      const gulupaTotal = shipmentsData
        .filter((s: ShipmentData) => s.productSent === 'gulupa')
        .reduce((acc: number, curr: ShipmentData) => acc + curr.totalKilosSent, 0);

      setTotalUchuva(uchuvaTotal);
      setTotalGulupa(gulupaTotal);
    } catch (error) {
      toast.error('Error loading dashboard data');
    } finally {
      setLoading(false);
    }
  };

  const handleExportExcel = () => {
    try {
      exportToExcel(shipments, `shipments-producer-${user!.id}`);
      toast.success('Excel report downloaded successfully');
    } catch (error) {
      toast.error('Error generating Excel report');
    }
  };

  const handleExportPDF = () => {
    try {
      exportToPDF(shipments, `shipments-producer-${user!.id}`);
      toast.success('PDF report downloaded successfully');
    } catch (error) {
      toast.error('Error generating PDF report');
    }
  };

  const getMonthlyData = (product: 'uchuva' | 'gulupa') => {
    const monthlyTotals = new Array(12).fill(0);
    
    shipments.forEach((shipment) => {
      if (shipment.productSent === product) {
        const month = new Date(shipment.dispatchDate).getMonth();
        monthlyTotals[month] += shipment.totalKilosSent;
      }
    });

    return monthlyTotals;
  };

  const months = [
    'January', 'February', 'March', 'April', 'May', 'June',
    'July', 'August', 'September', 'October', 'November', 'December'
  ];

  const chartOptions = {
    responsive: true,
    plugins: {
      legend: {
        position: 'top' as const,
      },
    },
    scales: {
      y: {
        beginAtZero: true,
        title: {
          display: true,
          text: 'Kilos'
        }
      }
    }
  };

  const uchuvaData = {
    labels: months,
    datasets: [
      {
        label: 'Uchuva Monthly Shipments',
        data: getMonthlyData('uchuva'),
        borderColor: 'rgb(255, 159, 64)',
        backgroundColor: 'rgba(255, 159, 64, 0.5)',
        tension: 0.1
      }
    ]
  };

  const gulupaData = {
    labels: months,
    datasets: [
      {
        label: 'Gulupa Monthly Shipments',
        data: getMonthlyData('gulupa'),
        borderColor: 'rgb(75, 192, 192)',
        backgroundColor: 'rgba(75, 192, 192, 0.5)',
        tension: 0.1
      }
    ]
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Producer Dashboard</h1>
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
      
      {/* Summary Cards */}
      <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
        <DashboardCard
          title="Total Uchuva"
          value={`${totalUchuva.toFixed(2)} kg`}
          icon={<Apple className="w-8 h-8 text-orange-500" />}
        />
        <DashboardCard
          title="Total Gulupa"
          value={`${totalGulupa.toFixed(2)} kg`}
          icon={<Apple className="w-8 h-8 text-purple-500" />}
        />
        <DashboardCard
          title="Registered Farms"
          value={farms.length.toString()}
          icon={<Tractor className="w-8 h-8 text-green-500" />}
        />
        <DashboardCard
          title="Active Crops"
          value={crops.length.toString()}
          icon={<Sprout className="w-8 h-8 text-emerald-500" />}
        />
      </div>

      {/* Charts */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white shadow rounded-lg p-6">
          <h2 className="text-xl font-semibold mb-4">Uchuva Monthly Shipments</h2>
          <Line options={chartOptions} data={uchuvaData} />
        </div>
        <div className="bg-white shadow rounded-lg p-6">
          <h2 className="text-xl font-semibold mb-4">Gulupa Monthly Shipments</h2>
          <Line options={chartOptions} data={gulupaData} />
        </div>
      </div>

      {/* Farms and Crops Summary */}
      <div className="grid grid-cols-1 lg:grid-cols-2 gap-6">
        <div className="bg-white shadow rounded-lg p-6">
          <h2 className="text-xl font-semibold mb-4">Registered Farms</h2>
          {farms.length === 0 ? (
            <p className="text-gray-500">No farms registered yet.</p>
          ) : (
            <div className="space-y-4">
              {farms.map((farm) => (
                <div key={farm.id} className="border-b pb-4">
                  <h3 className="font-semibold">{farm.farmName}</h3>
                  <p className="text-sm text-gray-600">
                    Municipality: {farm.municipality} | Hectares: {farm.hectares}
                  </p>
                </div>
              ))}
            </div>
          )}
        </div>
        <div className="bg-white shadow rounded-lg p-6">
          <h2 className="text-xl font-semibold mb-4">Active Crops</h2>
          {crops.length === 0 ? (
            <p className="text-gray-500">No crops registered yet.</p>
          ) : (
            <div className="space-y-4">
              {crops.map((crop) => {
                const farm = farms.find(f => f.id === crop.farmId);
                return (
                  <div key={crop.id} className="border-b pb-4">
                    <h3 className="font-semibold capitalize">{crop.product}</h3>
                    <p className="text-sm text-gray-600">
                      Farm: {farm?.farmName || 'Unknown'} | Status: {crop.status} |
                      Plants: {crop.plantCount} | Hectares: {crop.hectares}
                    </p>
                  </div>
                );
              })}
            </div>
          )}
        </div>
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

export default ProducerDashboard;