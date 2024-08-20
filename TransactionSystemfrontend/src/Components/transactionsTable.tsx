import React, { useEffect, useState } from 'react';



const RecentTransactions = () => {
    const [transactions , setTransactions] = useState([]);

    useEffect(() => {
        const fetchTransactions = async () => {
            try {
                const response = await fetch('http://localhost:8080/info/get-all', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Userid': localStorage.getItem('wallet-id') || '',
                    },
                });

                const data = await response.json();
                setTransactions(data);
            } catch (error) {
                console.error('Failed to fetch transactions:', error);
            }
        };

        fetchTransactions();
    }, []);

    // @ts-ignore
    return (
        <div className="flex-1 min-w-0 p-4 bg-white rounded-lg shadow-md">
            <h3 className="text-lg font-semibold mb-4">Recent Transactions</h3>
            <table className="min-w-full divide-y divide-gray-200">
                <thead className="bg-gray-50">
                <tr>

                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Description</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Amount</th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Exchange Rate</th>
                </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                {transactions.map((transaction) => (
                    <tr key={transaction}>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{transaction.date}</td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{transaction.description}</td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">${parseFloat(transaction.amount).toFixed(2)}</td>
                        <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-500">{transaction.exchangerate}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default RecentTransactions;
