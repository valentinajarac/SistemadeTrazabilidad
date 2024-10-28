import React, { useState } from 'react';

interface FarmData {
  id: string;
  farmName: string;
  hectares: string;
  municipality: string;
}

const Farm: React.FC = () => {
  const [farms, setFarms] = useState<FarmData[]>([]);
  const [formData, setFormData] = useState<FarmData>({
    id: '',
    farmName: '',
    hectares: '',
    municipality: '',
  });
  const [editingId, setEditingId] = useState<string | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (editingId) {
      setFarms(prevFarms => prevFarms.map(farm => 
        farm.id === editingId ? { ...formData, id: editingId } : farm
      ));
      setEditingId(null);
    } else {
      const newFarm = { ...formData, id: Date.now().toString() };
      setFarms(prevFarms => [...prevFarms, newFarm]);
    }
    setFormData({ id: '', farmName: '', hectares: '', municipality: '' });
  };

  const handleEdit = (farm: FarmData) => {
    setFormData(farm);
    setEditingId(farm.id);
  };

  const handleDelete = (id: string) => {
    setFarms(prevFarms => prevFarms.filter(farm => farm.id !== id));
  };

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold">Farm Management</h1>
      <div className="bg-white shadow rounded-lg p-6">
        <h2 className="text-xl font-semibold mb-4">
          {editingId ? 'Update Farm' : 'Register Farm'}
        </h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="farmName" className="block text-sm font-medium text-gray-700">Farm Name</label>
            <input
              type="text"
              id="farmName"
              name="farmName"
              value={formData.farmName}
              onChange={handleChange}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
              required
            />
          </div>
          <div>
            <label htmlFor="hectares" className="block text-sm font-medium text-gray-700">Hectares</label>
            <input
              type="number"
              id="hectares"
              name="hectares"
              value={formData.hectares}
              onChange={handleChange}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
              required
            />
          </div>
          <div>
            <label htmlFor="municipality" className="block text-sm font-medium text-gray-700">Municipality</label>
            <input
              type="text"
              id="municipality"
              name="municipality"
              value={formData.municipality}
              onChange={handleChange}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
              required
            />
          </div>
          <button type="submit" className="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50">
            {editingId ? 'Update Farm' : 'Register Farm'}
          </button>
        </form>
      </div>
      <div className="bg-white shadow rounded-lg p-6 mt-6">
        <h2 className="text-xl font-semibold mb-4">Registered Farms</h2>
        {farms.length === 0 ? (
          <p>No farms registered yet.</p>
        ) : (
          <ul className="space-y-4">
            {farms.map((farm) => (
              <li key={farm.id} className="border-b pb-4">
                <div className="flex justify-between items-center">
                  <div>
                    <strong>{farm.farmName}</strong>
                    <p>Hectares: {farm.hectares}</p>
                    <p>Municipality: {farm.municipality}</p>
                  </div>
                  <div>
                    <button
                      onClick={() => handleEdit(farm)}
                      className="bg-yellow-500 text-white py-1 px-2 rounded-md mr-2 hover:bg-yellow-600 focus:outline-none focus:ring-2 focus:ring-yellow-500 focus:ring-opacity-50"
                    >
                      Edit
                    </button>
                    <button
                      onClick={() => handleDelete(farm.id)}
                      className="bg-red-500 text-white py-1 px-2 rounded-md hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-opacity-50"
                    >
                      Delete
                    </button>
                  </div>
                </div>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

export default Farm;