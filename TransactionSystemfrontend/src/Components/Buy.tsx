import React, { useState, useEffect } from 'react';
import UserLedgersPage from "./BalanceComponent.tsx";

interface BuyRequest {
    walletid: string;
    amount: number;
    ledcode: string;
}

const fxRates: Record<string, string> = {
    BITCOIN: '0.000017',
    ETH: '0.00038',
    FOX: '120.1718'
};





const BuyPage: React.FC = () => {
    const [walletId] = useState(localStorage.getItem('wallet-id') || ''); // Assuming wallet ID is stored in localStorage
    const [amount, setAmount] = useState<number>(0);
    const [ledger, setLedger] = useState<string>('BITCOIN');
    const [fxRate, setFxRate] = useState<string>(fxRates['BITCOIN']);
    const [error, setError] = useState<string | null>(null);// Default FX rate for BITCOIN

    useEffect(() => {
        // Update the FX rate when the ledger changes
        setFxRate(fxRates[ledger]);
    }, [ledger]);

    const handleSubmit = async () => {
        const requestData: BuyRequest = {
            walletid: walletId,
            amount: amount,
            ledcode: ledger
        };

        try {
            const response = await fetch('http://localhost:8080/api/account/buy-coins', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            });

            if (response.ok) {
                let data = await response.json();

                if (data === null) {
                    setError("Not enough balance");
                } else {
                    console.log('Success:', data);
                    setError("Buy Process Completed Successfully");

                    setTimeout(() => {
                        window.location.href = '/buy';
                    }, 3000);
                }
            } else {
                console.error('Error:', response.statusText);
                setError("Error Happened!");
            }
        } catch (error) {
            console.error('Error:', error);
            setError("Error Happened!");
        }
    };

    return (
        <>
            <UserLedgersPage/>
            <div className="flex items-center justify-center min-h-svh bg-gray-100">
                <div className="bg-white shadow-lg rounded-lg w-full max-w-md p-6">
                    <div className="mb-4">
                        <div className="text-sm text-gray-500 mb-2">FX Rate: {fxRate}</div>
                    </div>
                    <div className="space-y-4">
                        <div>
                            <label htmlFor="walletId" className="block text-sm font-medium text-gray-700">
                                Wallet ID
                            </label>
                            <input
                                id="walletId"
                                type="text"
                                value={walletId}
                                readOnly
                                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                            />
                        </div>

                        <div>
                            <label htmlFor="amount" className="block text-sm font-medium text-gray-700">
                                Amount
                            </label>
                            <input
                                id="amount"
                                type="number"
                                value={amount}
                                onChange={(e) => setAmount(parseFloat(e.target.value))}
                                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                            />
                        </div>

                        <div>
                            <label htmlFor="ledger" className="block text-sm font-medium text-gray-700">
                                Ledger
                            </label>
                            <select
                                id="ledger"
                                value={ledger}
                                onChange={(e) => setLedger(e.target.value)}
                                className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                            >
                                <option value="BITCOIN">BITCOIN</option>
                                <option value="ETH">ETH</option>
                                <option value="FOX">FOX</option>
                            </select>
                        </div>

                        <button
                            type="button"
                            onClick={handleSubmit}
                            className="w-full bg-indigo-600 text-white py-2 px-4 rounded-md hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-indigo-500"
                        >
                            Buy
                        </button>

                        {error && <p className="mt-10 text-center text-sm text-red-500">{error}</p>}

                    </div>
                </div>
            </div>

        </>
    );
};

export default BuyPage;
