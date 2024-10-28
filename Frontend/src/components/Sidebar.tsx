import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { Home, Tractor, Sprout, Truck, Users, Briefcase } from 'lucide-react';

const Sidebar: React.FC = () => {
  const { user } = useAuth();
  const location = useLocation();

  if (!user) return null;

  const isActive = (path: string) => location.pathname === path;

  const producerLinks = [
    { path: '/producer', icon: Home, label: 'Dashboard' },
    { path: '/producer/farm', icon: Tractor, label: 'Farm' },
    { path: '/producer/crops', icon: Sprout, label: 'Crops' },
    { path: '/producer/fruit-shipment', icon: Truck, label: 'Fruit Shipment' },
  ];

  const adminLinks = [
    { path: '/admin', icon: Home, label: 'Dashboard' },
    { path: '/admin/users', icon: Users, label: 'User Management' },
    { path: '/admin/clients', icon: Briefcase, label: 'Client Management' },
  ];

  const links = user.type === 'admin' ? adminLinks : producerLinks;

  return (
    <div className="bg-black text-white w-64 min-h-screen p-4">
      <div className="flex items-center mb-8">
        <img src="logo.png" alt="Logo" className="w-10 h-10 mr-2" />
        <span className="font-bold text-xl">Sistema de Trazabilidad</span>
      </div>
      <nav>
        <ul className="space-y-2">
          {links.map((link) => (
            <li key={link.path}>
              <Link
                to={link.path}
                className={`flex items-center p-2 rounded-lg ${
                  isActive(link.path) ? 'bg-green-600' : 'hover:bg-gray-800'
                }`}
              >
                <link.icon className="w-5 h-5 mr-3" />
                {link.label}
              </Link>
            </li>
          ))}
        </ul>
      </nav>
    </div>
  );
};

export default Sidebar;