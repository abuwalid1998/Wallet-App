import React from 'react';
import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend } from 'chart.js';
import '../Functions/charts.js';
import {Line} from "react-chartjs-2";
import RecentTransactions from "./transactionsTable.tsx";

//wallet-id

ChartJS.register(
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
    Title,
    Tooltip,
    Legend
);

const mockData = {
    labels: ["January", "February", "March", "April", "May"],
    datasets: [
        {
            label: "Transactions",
            data: [10, 20, 15, 25, 30],
            borderColor: "#4A90E2",
            backgroundColor: "rgba(74, 144, 226, 0.2)",
            fill: true,
        },
    ],
};


const Dashboard: React.FC = () => {
    const walletId = localStorage.getItem('wallet-id') || 'No Wallet ID';

    return (
        <div className="p-6 space-y-6">
            <div className="flex flex-wrap gap-6">
                {/* Chart Section */}
                <div className="flex-1 min-w-0 p-4 bg-white rounded-lg shadow-md">
                    <h3 className="text-lg font-semibold mb-4">Transaction Overview</h3>
                    <Line  data ={mockData}/>
                </div>

                {/* Table Section */}
                <RecentTransactions/>

            </div>

            {/* Banner Section */}
            <div className="p-4 bg-blue-500 text-white rounded-lg text-center">
                <p className="text-lg font-semibold">Wallet ID: {walletId}</p>
            </div>
        </div>
    );
};

export default Dashboard;
