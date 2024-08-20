// src/components/LineChart.tsx
import React from 'react';
import { Line } from 'react-chartjs-2';

// Import Chart.js components and register them (if not already done globally)
import '../Functions/charts.js'; // Ensure this import is included to register Chart.js components

const mockData = {
    labels: ['January', 'February', 'March', 'April', 'May'],
    datasets: [
        {
            label: 'Transactions',
            data: [65, 59, 80, 81, 56],
            borderColor: 'rgb(75, 192, 192)',
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
        },
    ],
};

const LineChart: React.FC = () => {
    return (
        <div className="bg-white p-6 rounded-lg shadow-md">
            <h2 className="text-lg font-bold mb-4">Monthly Transactions</h2>
            <Line data={mockData} />
        </div>
    );
};

export default LineChart;
