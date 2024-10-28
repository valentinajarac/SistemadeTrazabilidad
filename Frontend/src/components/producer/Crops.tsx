import React, { useState } from 'react';

interface CropData {
  id: string;
  plantCount: string;
  hectares: string;
  product: 'uchuva' | 'gulupa';
  status: 'produccion' | 'vegetacion';
  farmId: string;
}

const Crops: React.FC = () => {
  const [crops, setCrops] = useState<CropData[]>([]);
  const [formData, setFormData] = useState<CropData>({
    id: '',
    plantCount: '',
    hectares: '',
    product: 'uchuva',
    status: 'vegetacion',
    farmId: '',
  });
  const [editingId, setEditingId] = useState<string | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (editingId) {
      setCrops(prevCrops => prevCrops.map(crop => 
        crop.id === editingId ? { ...formData, id: editingId } : crop
      ));
      setEditingId(null);
    } else {
      const newCrop = { ...formData, id: Date.now().toString() };
      setCrops(prevCrops => [...prevCrops, newCrop]);
    }
    setFormData({
      id: '',
      plantCount: '',
      hectares: '',
      product: 'uchuva',
      status: 'vegetacion',
      farmId: '',
    });
  };

  const handleEdit = (crop: CropData) => {
    setFormData(crop);
    setEditingId(crop.id);
  };

  const handleDelete = (id: string) => {
    setCrops(prevCrops => prevCrops.filter(crop => crop.id !== id));
  };

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold">Crop Management</h1>
      <div className="bg-white shadow rounded-lg p-6">
        <h2 className="text-xl font-semibold mb-4">
          {editingId ? 'Update Crop' : 'Register Crop'}
        </h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div>
            <label htmlFor="plantCount" className="block text-sm font-medium text-gray-700">Cantidad de plantas</label>
            <input
              type="number"
              id="plantCount"
              name="plantCount"
              value={formData.plantCount}
              onChange={handleChange}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
              required
            />
          </div>
          <div>
            <label htmlFor="hectares" className="block text-sm font-medium text-gray-700">Hectáreas del cultivo</label>
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
            <label htmlFor="product" className="block text-sm font-medium text-gray-700">Producto</label>
            <select
              id="product"
              name="product"
              value={formData.product}
              onChange={handleChange}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
              required
            >
              <option value="uchuva">Uchuva</option>
              <option value="gulupa">Gulupa</option>
            </select>
          </div>
          <div>
            <label htmlFor="status" className="block text-sm font-medium text-gray-700">Estado</label>
            <select
              id="status"
              name="status"
              value={formData.status}
              onChange={handleChange}
              className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
              required
            >
              <option value="produccion">Producción</option>
              <option value="vegetacion">Vegetación</option>
            </select>
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
          <button type="submit" className="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50">
            {editingId ? 'Update Crop' : 'Register Crop'}
          </button>
        </form>
      </div>
      <div className="bg-white shadow rounded-lg p-6 mt-6">
        <h2 className="text-xl font-semibold mb-4">Registered Crops</h2>
        {crops.length === 0 ? (
          <p>No crops registered yet.</p>
        ) : (
          <ul className="space-y-4">
            {crops.map((crop) => (
              <li key={crop.id} className="border-b pb-4">
                <div className="flex justify-between items-center">
                  <div>
                    <p><strong>ID:</strong> {crop.id}</p>
                    <p><strong>Plantas:</strong> {crop.plantCount}</p>
                    <p><strong>Hectáreas:</strong> {crop.hectares}</p>
                    <p><strong>Producto:</strong> {crop.product}</p>
                    <p><strong>Estado:</strong> {crop.status}</p>
                    <p><strong>Farm ID:</strong> {crop.farmId}</p>
                  </div>
                  <div>
                    <button
                      onClick={() => handleEdit(crop)}
                      className="bg-yellow-500 text-white py-1 px-2 rounded-md mr-2 hover:bg-yellow-600 focus:outline-none focus:ring-2 focus:ring-yellow-500 focus:ring-opacity-50"
                    >
                      Edit
                    </button>
                    <button
                      onClick={() => handleDelete(crop.id)}
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

export default Crops;