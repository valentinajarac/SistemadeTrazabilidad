import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

const Navbar: React.FC = () => {
  const { user, logout } = useAuth();

  return (
    <nav className="bg-gray-300 text-gray-800 shadow-lg">
      <div className="container mx-auto px-6 py-3 flex justify-between items-center">
        <Link to="/" className="flex items-center">
          <img src="logo.png" alt="Logo" className="w-10 h-10 mr-2" />
          <span className="font-bold text-xl">Sistema de Trazabilidad</span>
        </Link>
        <div>
          {user ? (
            <div className="flex items-center">
              <span className="mr-4">Welcome, {user.name}</span>
              <button
                onClick={logout}
                className="bg-red-500 hover:bg-red-600 text-white font-bold py-2 px-4 rounded"
              >
                Logout
              </button>
            </div>
          ) : (
            <Link to="/login" className="hover:text-gray-600">Login</Link>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;