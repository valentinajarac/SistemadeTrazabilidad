import React, { createContext, useState, useContext, ReactNode } from 'react';
import { login as apiLogin, setAuthToken, clearAuthToken } from '../services/api';
import toast from 'react-hot-toast';

interface User {
  id: string;
  name: string;
  type: 'admin' | 'producer';
  token: string;
}

interface AuthContextType {
  user: User | null;
  login: (username: string, password: string) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

// Mock users for testing
const mockUsers = {
  admin: {
    id: '1',
    name: 'Admin User',
    type: 'admin' as const,
    username: 'admin',
    password: 'admin123',
    token: 'mock-admin-token'
  },
  producer: {
    id: '2',
    name: 'Producer User',
    type: 'producer' as const,
    username: 'producer',
    password: 'producer123',
    token: 'mock-producer-token'
  }
};

export const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<User | null>(() => {
    const savedUser = localStorage.getItem('user');
    if (savedUser) {
      const parsedUser = JSON.parse(savedUser);
      setAuthToken(parsedUser.token);
      return parsedUser;
    }
    return null;
  });

  const login = async (username: string, password: string) => {
    try {
      // For testing purposes, we'll use mock authentication
      const mockUser = Object.values(mockUsers).find(
        u => u.username === username && u.password === password
      );

      if (!mockUser) {
        throw new Error('Invalid credentials');
      }

      const userData = {
        id: mockUser.id,
        name: mockUser.name,
        type: mockUser.type,
        token: mockUser.token,
      };

      setUser(userData);
      setAuthToken(mockUser.token);
      localStorage.setItem('user', JSON.stringify(userData));
      toast.success(`Welcome back, ${mockUser.name}!`);
    } catch (error) {
      toast.error('Invalid credentials');
      throw error;
    }
  };

  const logout = () => {
    setUser(null);
    clearAuthToken();
    localStorage.removeItem('user');
    toast.success('Logged out successfully');
  };

  return (
    <AuthContext.Provider value={{ user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};