import React, { useEffect, useState } from 'react';

interface Ledger {
    ledgerId: string;
    balance: number;
    currency: string;
    // Add other fields as per the API response
}

const UserLedgersPage: React.FC = () => {
    const [ledgers, setLedgers] = useState<Ledger[]>([]);
    const [error, setError] = useState<string | null>(null);
    const walletId = localStorage.getItem('wallet-id') || '';

    const fetchUserLedgers = async (walletId : string): Promise<Ledger[]> => {
        try {
            const response = await fetch('http://localhost:8080/info/get-user-ledgers', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'walletid': walletId, // Pass the walletId in the headers
                },
            });

            if (!response.ok) {
                throw new Error(`Error fetching ledgers: ${response.statusText}`);
            }

            const data: Ledger[] = await response.json();
            return data;
        } catch (error) {
            console.error('Fetch error:', error);
            throw error;
        }
    };

    useEffect(() => {
        const getLedgers = async () => {
            try {
                const ledgerData = await fetchUserLedgers(walletId);
                setLedgers(ledgerData);
            } catch (error) {
                setError('Failed to fetch ledgers');
            }
        };

        getLedgers();
    }, [walletId]);

    return (
        <div className="p-4">
            <h1 className="text-2xl font-semibold mb-4">User Balances</h1>
            {error && <p className="text-red-500">{error}</p>}
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-4">
                {ledgers.map((ledger) => (
                    <div key={ledger.ledgerId} className="bg-white shadow-lg rounded-lg p-4 text-center">
                        <h3 className="text-lg font-semibold text-gray-700">{ledger.currency}</h3>
                        <p className="text-sm text-gray-500">{ledger.balance} {ledger.currency}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default UserLedgersPage;
