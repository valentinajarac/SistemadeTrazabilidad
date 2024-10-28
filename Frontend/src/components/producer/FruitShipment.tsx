import React, { useState, useEffect } from 'react';

interface ShipmentData {
  id: string;
  dispatchDate: string;
  producerId: string;
  clientId: string;
  farmId: string;
  cropId: string;
  basketsSent: number;
  productSent: 'uchuva' | 'gulupa';
  averageWeight: number;
  totalKilosSent: number;
}

const FruitShipment: React.FC = () => {
  const [shipments, setShipments] = useState<ShipmentData[]>([]);
  const [formData, setFormData] = useState<ShipmentData>({
    id: '',
    dispatchDate: '',
    producerId: '',
    clientId: '',
    farmId: '',
    cropId: '',
    basketsSent: 0,
    productSent: 'uchuva',
    averageWeight: 0,
    totalKilosSent: 0,
  });
  const [editingId, setEditingId] = useState<string | null>(null);

  useEffect(() => {
    const storedShipments = JSON.parse(localStorage.getItem('shipments') || '[]');
    setShipments(storedShipments);
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: name === 'basketsSent' || name === 'averageWeight' || name === 'totalKilosSent' 
        ? parseFloat(value) 
        : value
    }));
  };

  const updateShipments = (newShipments: ShipmentData[]) => {
    setShipments(newShipments);
    localStorage.setItem('shipments', JSON.stringify(newShipments));
    window.dispatchEvent(new Event('shipmentsUpdated'));
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (editingId) {
      const updatedShipments = shipments.map(shipment => 
        shipment.id === editingId ? { ...formData, id: editingId } : shipment
      );
      updateShipments(updatedShipments);
      setEditingId(null);
    } else {
      const newShipment = { ...formData, id: Date.now().toString() };
      updateShipments([...shipments, newShipment]);
    }
    setFormData({
      id: '',
      dispatchDate: '',
      producerId: '',
      clientId: '',
      farmId: '',
      cropId: '',
      basketsSent: 0,
      productSent: 'uchuva',
      averageWeight: 0,
      totalKilosSent: 0,
    });
  };

  const handleEdit = (shipment: ShipmentData) => {
    setFormData(shipment);
    setEditingId(shipment.id);
  };

  const handleDelete = (id: string) => {
    const updatedShipments = shipments.filter(shipment => shipment.id !== id);
    updateShipments(updatedShipments);
  };

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold">Fruit Shipment</h1>
      <div className="bg-white shadow rounded-lg p-6">
        <h2 className="text-xl font-semibold mb-4">
          {editingId ? 'Update Shipment' : 'Register Shipment'}
        </h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label htmlFor="dispatchDate" className="block text-sm font-medium text-gray-700">Dispatch Date</label>
              <input
                type="date"
                id="dispatchDate"
                name="dispatchDate"
                value={formData.dispatchDate}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                required
              />
            </div>
            <div>
              <label htmlFor="producerId" className="block text-sm font-medium text-gray-700">Producer ID</label>
              <input
                type="text"
                id="producerId"
                name="producerId"
                value={formData.producerId}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                required
              />
            </div>
            <div>
              <label htmlFor="clientId" className="block text-sm font-medium text-gray-700">Client ID</label>
              <input
                type="text"
                id="clientId"
                name="clientId"
                value={formData.clientId}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                required
              />
            </div>
            <div>
              <label htmlFor="farmId" className="block text-sm font-medium text-gray-700">Farm ID</label>
              <input
                type="text"
                id="farmId"
                name="farmId"
                value={formData.farmId}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                required
              />
            </div>
            <div>
              <label htmlFor="cropId" className="block text-sm font-medium text-gray-700">Crop ID</label>
              <input
                type="text"
                id="cropId"
                name="cropId"
                value={formData.cropId}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                required
              />
            </div>
            <div>
              <label htmlFor="basketsSent" className="block text-sm font-medium text-gray-700">Baskets Sent</label>
              <input
                type="number"
                id="basketsSent"
                name="basketsSent"
                value={formData.basketsSent}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                required
              />
            </div>
            <div>
              <label htmlFor="productSent" className="block text-sm font-medium text-gray-700">Product Sent</label>
              <select
                id="productSent"
                name="productSent"
                value={formData.productSent}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                required
              >
                <option value="uchuva">Uchuva</option>
                <option value="gulupa">Gulupa</option>
              </select>
            </div>
            <div>
              <label htmlFor="averageWeight" className="block text-sm font-medium text-gray-700">Average Weight (kg)</label>
              <input
                type="number"
                id="averageWeight"
                name="averageWeight"
                value={formData.averageWeight}
                onChange={handleChange}
                step="0.01"
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                required
              />
            </div>
            <div>
              <label htmlFor="totalKilosSent" className="block text-sm font-medium text-gray-700">Total Kilos Sent</label>
              <input
                type="number"
                id="totalKilosSent"
                name="totalKilosSent"
                value={formData.totalKilosSent}
                onChange={handleChange}
                step="0.01"
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                required
              />
            </div>
          </div>
          <button type="submit" className="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50">
            {editingId ? 'Update Shipment' : 'Register Shipment'}
          </button>
        </form>
      </div>
      <div className="bg-white shadow rounded-lg p-6 mt-6">
        <h2 className="text-xl font-semibold mb-4">Registered Shipments</h2>
        {shipments.length === 0 ? (
          <p>No shipments registered yet.</p>
        ) : (
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date</th>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Product</th>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Total Kilos</th>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {shipments.map((shipment) => (
                  <tr key={shipment.id}>
                    <td className="px-6 py-4 whitespace-nowrap">{shipment.dispatchDate}</td>
                    <td className="px-6 py-4 whitespace-nowrap">{shipment.productSent}</td>
                    <td className="px-6 py-4 whitespace-nowrap">{shipment.totalKilosSent} kg</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <button
                        onClick={() => handleEdit(shipment)}
                        className="text-indigo-600 hover:text-indigo-900 mr-2"
                      >
                        Edit
                      </button>
                      <button
                        onClick={() => handleDelete(shipment.id)}
                        className="text-red-600 hover:text-red-900"
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </div>
  );
};

export default FruitShipment;