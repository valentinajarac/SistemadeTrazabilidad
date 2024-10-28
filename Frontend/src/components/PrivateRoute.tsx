import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

interface PrivateRouteProps {
  children: React.ReactNode;
  userType: 'admin' | 'producer';
}

const PrivateRoute: React.FC<PrivateRouteProps> = ({ children, userType }) => {
  const { user } = useAuth();

  if (!user) {
    return <Navigate to="/login" replace />;
  }

  if (user.type !== userType) {
    return <Navigate to={`/${user.type}`} replace />;
  }

  return <>{children}</>;
};

export default PrivateRoute;