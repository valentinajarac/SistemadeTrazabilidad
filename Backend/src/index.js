const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const helmet = require('helmet');
const mongoSanitize = require('express-mongo-sanitize');
require('dotenv').config();

const logger = require('./config/logger');
const limiter = require('./middleware/rateLimiter');
const errorHandler = require('./middleware/errorHandler');
const authRoutes = require('./routes/auth.routes');
const userRoutes = require('./routes/user.routes');
const farmRoutes = require('./routes/farm.routes');
const cropRoutes = require('./routes/crop.routes');
const remissionRoutes = require('./routes/remission.routes');
const clientRoutes = require('./routes/client.routes');
const reportRoutes = require('./routes/report.routes');

const app = express();

// Security Middleware
app.use(helmet());
app.use(mongoSanitize());
app.use(cors({
  origin: process.env.FRONTEND_URL || 'http://localhost:5173',
  credentials: true,
  methods: ['GET', 'POST', 'PUT', 'DELETE'],
  allowedHeaders: ['Content-Type', 'Authorization']
}));
app.use(limiter);

// Basic Middleware
app.use(express.json());

// Health Check
app.get('/health', (req, res) => {
  res.status(200).json({ status: 'OK', timestamp: new Date() });
});

// Database connection
mongoose.connect(process.env.MONGODB_URI || 'mongodb://localhost/fruit-traceability', {
  useNewUrlParser: true,
  useUnifiedTopology: true
}).then(() => {
  logger.info('Connected to MongoDB');
}).catch((error) => {
  logger.error('MongoDB connection error:', error);
  process.exit(1);
});

// Routes
app.use('/api/auth', authRoutes);
app.use('/api/users', userRoutes);
app.use('/api/farms', farmRoutes);
app.use('/api/crops', cropRoutes);
app.use('/api/remissions', remissionRoutes);
app.use('/api/clients', clientRoutes);
app.use('/api/reports', reportRoutes);

// Error Handler
app.use(errorHandler);

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  logger.info(`Server running on port ${PORT}`);
});