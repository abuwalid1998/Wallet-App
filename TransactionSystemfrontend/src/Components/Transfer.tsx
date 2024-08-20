import React, { useState } from 'react';
import UserLedgersPage from "./BalanceComponent.tsx";


interface TransferRequest {
    fromWalletId: string;
    toWalletId: string;
    amount: number;
    curcode: string;
}

const Transfer: React.FC = () => {
    const [sourceLedger, setSourceLedger] = useState<string>('BITCOIN');
    const [amount, setAmount] = useState<number>(0);
    const [sourceWalletId] = useState<string>(localStorage.getItem('wallet-id') || '');
    const [destinationWalletId, setDestinationWalletId] = useState<string>('');
    const [error, setError] = useState<string | null>(null);// Default FX rate for BITCOIN


    const ledgers = ['BITCOIN', 'ETH', 'FOX', 'USD'];

    const handleSubmit = async () => {
        console.log(sourceWalletId);
        console.log(destinationWalletId);
        const requestData: TransferRequest = {
            fromWalletId: sourceWalletId,
            toWalletId:destinationWalletId,
            amount: amount,
            curcode: sourceLedger
        };

        try {
            const response = await fetch('http://localhost:8080/api/account/Transfer-coins', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            });

            if (response.ok) {

                let data = await response.json();
                console.log(data);

                if (data === null) {
                    setError("Error Happen -> No Response Found !");
                } else {
                    console.log('Success:', data);
                    setError("Sell Process Completed Successfully" + data.description);

                    setTimeout(() => {
                        window.location.href = '/transfer';
                    }, 3000);
                }
            } else {
                console.error('Error:', response.statusText);
                setError("Error Happened -> Contact with Admins");
            }
        } catch (error) {
            console.error('Error:', error);
            setError("Error Happened  Try Again!");
        }
    };

    return (
        <>
            <UserLedgersPage/>
            <div className="flex items-center justify-center min-h-screen bg-gray-100 w-screen max-w-screen">
                <div className="grid grid-cols-1 sm:grid-cols-3 gap-8 w-full max-w-6xl">
                    {/* Source Card */}
                    <div className="bg-white shadow-lg rounded-lg w-full max-w-xl p-6 mx-auto">
                        <h3 className="text-lg font-semibold mb-4">Source Wallet</h3>
                        <div className="space-y-4">
                            <div>
                                <label htmlFor="sourceWalletId" className="block text-sm font-medium text-gray-700">
                                    Wallet ID
                                </label>
                                <input
                                    id="sourceWalletId"
                                    type="text"
                                    value={sourceWalletId}
                                    readOnly
                                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                                />
                            </div>
                            <div>
                                <label htmlFor="sourceLedger" className="block text-sm font-medium text-gray-700">
                                    Ledger
                                </label>
                                <select
                                    id="sourceLedger"
                                    value={sourceLedger}
                                    onChange={(e) => setSourceLedger(e.target.value)}
                                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                                >
                                    {ledgers.map((ledger) => (
                                        <option key={ledger} value={ledger}>
                                            {ledger}
                                        </option>
                                    ))}
                                </select>
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

                        </div>
                    </div>

                    {/* Transfer Image */}
                    <div className="flex justify-center items-center">
                        <img src="src/assets/transfer.png" alt="Transfer" className="h-16 w-16"/>
                    </div>

                    {/* Destination Card */}
                    <div className="bg-white shadow-lg rounded-lg w-full max-w-xl p-6 mx-auto">
                        <h3 className="text-lg font-semibold mb-4">Destination Wallet</h3>
                        <div className="space-y-4">
                            <div>
                                <label htmlFor="destinationWalletId"
                                       className="block text-sm font-medium text-gray-700">
                                    Wallet ID
                                </label>
                                <input
                                    id="destinationWalletId"
                                    type="text"
                                    value={destinationWalletId}
                                    onChange={(e) => setDestinationWalletId(e.target.value)}
                                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                                />
                            </div>
                            <div>
                                <label htmlFor="destinationLedger" className="block text-sm font-medium text-gray-700">
                                    Ledger
                                </label>
                                <select
                                    id="destinationLedger"
                                    disabled={true}
                                    value={sourceLedger}
                                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm"
                                >
                                    {ledgers.map((ledger) => (
                                        <option key={ledger} value={ledger}>
                                            {ledger}
                                        </option>
                                    ))}
                                </select>
                            </div>
                        </div>
                    </div>
                    <button
                        onClick={handleSubmit}
                        className="mt-4 bg-blue-500 text-white px-4 py-2 rounded-md shadow-sm hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-offset-2"
                    >
                        Transfer
                    </button>
                    {error && <p className="mt-10 text-center text-sm text-red-500">{error}</p>}
                </div>
            </div>

        </>
    );
};

export default Transfer;
