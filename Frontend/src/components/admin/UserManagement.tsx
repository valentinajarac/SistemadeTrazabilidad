import React, { useState, useEffect } from 'react';

interface UserData {
  id: string;
  idNumber: string;
  fullName: string;
  traceabilityCode: string;
  municipality: string;
  phone: string;
  username: string;
  password: string;
}

const UserManagement: React.FC = () => {
  const [users, setUsers] = useState<UserData[]>([]);
  const [formData, setFormData] = useState<UserData>({
    id: '',
    idNumber: '',
    fullName: '',
    traceabilityCode: '',
    municipality: '',
    phone: '',
    username: '',
    password: '',
  });
  const [editingId, setEditingId] = useState<string | null>(null);

  useEffect(() => {
    const storedUsers = JSON.parse(localStorage.getItem('users') || '[]');
    setUsers(storedUsers);
  }, []);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prevState => ({
      ...prevState,
      [name]: value
    }));
  };

  const updateUsers = (newUsers: UserData[]) => {
    setUsers(newUsers);
    localStorage.setItem('users', JSON.stringify(newUsers));
    window.dispatchEvent(new Event('usersUpdated'));
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (editingId) {
      const updatedUsers = users.map(user => 
        user.id === editingId ? { ...formData, id: editingId } : user
      );
      updateUsers(updatedUsers);
      setEditingId(null);
    } else {
      const newUser = { ...formData, id: Date.now().toString() };
      updateUsers([...users, newUser]);
    }
    setFormData({
      id: '',
      idNumber: '',
      fullName: '',
      traceabilityCode: '',
      municipality: '',
      phone: '',
      username: '',
      password: '',
    });
  };

  const handleEdit = (user: UserData) => {
    setFormData(user);
    setEditingId(user.id);
  };

  const handleDelete = (id: string) => {
    const updatedUsers = users.filter(user => user.id !== id);
    updateUsers(updatedUsers);
  };

  return (
    <div className="space-y-6">
      <h1 className="text-3xl font-bold">User Management</h1>
      <div className="bg-white shadow rounded-lg p-6">
        <h2 className="text-xl font-semibold mb-4">
          {editingId ? 'Update Producer' : 'Register Producer'}
        </h2>
        <form onSubmit={handleSubmit} className="space-y-4">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div>
              <label htmlFor="idNumber" className="block text-sm font-medium text-gray-700">Cédula del Productor</label>
              <input
                type="text"
                id="idNumber"
                name="idNumber"
                value={formData.idNumber}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                required
              />
            </div>
            <div>
              <label htmlFor="fullName" className="block text-sm font-medium text-gray-700">Nombre Completo</label>
              <input
                type="text"
                id="fullName"
                name="fullName"
                value={formData.fullName}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                required
              />
            </div>
            <div>
              <label htmlFor="traceabilityCode" className="block text-sm font-medium text-gray-700">Código de Trazabilidad</label>
              <input
                type="text"
                id="traceabilityCode"
                name="traceabilityCode"
                value={formData.traceabilityCode}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                required
              />
            </div>
            <div>
              <label htmlFor="municipality" className="block text-sm font-medium text-gray-700">Municipio</label>
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
            <div>
              <label htmlFor="phone" className="block text-sm font-medium text-gray-700">Teléfono</label>
              <input
                type="tel"
                id="phone"
                name="phone"
                value={formData.phone}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                required
              />
            </div>
            <div>
              <label htmlFor="username" className="block text-sm font-medium text-gray-700">Usuario</label>
              <input
                type="text"
                id="username"
                name="username"
                value={formData.username}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                required
              />
            </div>
            <div>
              <label htmlFor="password" className="block text-sm font-medium text-gray-700">Contraseña</label>
              <input
                type="password"
                id="password"
                name="password"
                value={formData.password}
                onChange={handleChange}
                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-300 focus:ring focus:ring-blue-200 focus:ring-opacity-50"
                required
              />
            </div>
          </div>
          <button type="submit" className="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50">
            {editingId ? 'Update Producer' : 'Register Producer'}
          </button>
        </form>
      </div>
      <div className="bg-white shadow rounded-lg p-6 mt-6">
        <h2 className="text-xl font-semibold mb-4">Registered Producers</h2>
        {users.length === 0 ? (
          <p>No producers registered yet.</p>
        ) : (
          <div className="overflow-x-auto">
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Cédula</th>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Nombre</th>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Código</th>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Municipio</th>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Teléfono</th>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Usuario</th>
                  <th scope="col" className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Acciones</th>
                </tr>
              </thead>
              <tbody className="bg-white divide-y divide-gray-200">
                {users.map((user) => (
                  <tr key={user.id}>
                    <td className="px-6 py-4 whitespace-nowrap">{user.idNumber}</td>
                    <td className="px-6 py-4 whitespace-nowrap">{user.fullName}</td>
                    <td className="px-6 py-4 whitespace-nowrap">{user.traceabilityCode}</td>
                    <td className="px-6 py-4 whitespace-nowrap">{user.municipality}</td>
                    <td className="px-6 py-4 whitespace-nowrap">{user.phone}</td>
                    <td className="px-6 py-4 whitespace-nowrap">{user.username}</td>
                    <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <button
                        onClick={() => handleEdit(user)}
                        className="text-indigo-600 hover:text-indigo-900 mr-2"
                      >
                        Editar
                      </button>
                      <button
                        onClick={() => handleDelete(user.id)}
                        className="text-red-600 hover:text-red-900"
                      >
                        Eliminar
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

export default UserManagement;