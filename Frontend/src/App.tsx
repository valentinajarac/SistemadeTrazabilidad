import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext';
import Login from './components/Login';
import AdminDashboard from './components/AdminDashboard';
import ProducerDashboard from './components/ProducerDashboard';
import Navbar from './components/Navbar';
import PrivateRoute from './components/PrivateRoute';
import Sidebar from './components/Sidebar';
import Farm from './components/producer/Farm';
import Crops from './components/producer/Crops';
import FruitShipment from './components/producer/FruitShipment';
import UserManagement from './components/admin/UserManagement';
import ClientManagement from './components/admin/ClientManagement';

function App() {
  return (
    <AuthProvider>
      <Router>
        <div className="min-h-screen bg-gray-100 flex">
          <Sidebar />
          <div className="flex-1">
            <Navbar />
            <div className="container mx-auto px-4 py-8">
              <Routes>
                <Route path="/login" element={<Login />} />
                <Route 
                  path="/admin" 
                  element={
                    <PrivateRoute userType="admin">
                      <AdminDashboard />
                    </PrivateRoute>
                  } 
                />
                <Route 
                  path="/admin/users" 
                  element={
                    <PrivateRoute userType="admin">
                      <UserManagement />
                    </PrivateRoute>
                  } 
                />
                <Route 
                  path="/admin/clients" 
                  element={
                    <PrivateRoute userType="admin">
                      <ClientManagement />
                    </PrivateRoute>
                  } 
                />
                <Route 
                  path="/producer" 
                  element={
                    <PrivateRoute userType="producer">
                      <ProducerDashboard />
                    </PrivateRoute>
                  } 
                />
                <Route 
                  path="/producer/farm" 
                  element={
                    <PrivateRoute userType="producer">
                      <Farm />
                    </PrivateRoute>
                  } 
                />
                <Route 
                  path="/producer/crops" 
                  element={
                    <PrivateRoute userType="producer">
                      <Crops />
                    </PrivateRoute>
                  } 
                />
                <Route 
                  path="/producer/fruit-shipment" 
                  element={
                    <PrivateRoute userType="producer">
                      <FruitShipment />
                    </PrivateRoute>
                  } 
                />
                <Route path="/" element={<Navigate to="/login" replace />} />
              </Routes>
            </div>
          </div>
        </div>
      </Router>
    </AuthProvider>
  );
}

export default App;