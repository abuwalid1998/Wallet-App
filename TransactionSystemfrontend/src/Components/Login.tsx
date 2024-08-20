import { useState } from 'react';

interface WalletResponse {
    id: string;
}

interface LoginResponse {
    token: string;
}

interface ErrorResponse {
    message: string;
}

export default function Login() {
    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [error, setError] = useState<string | null>(null);

    const getUserWalletId = async (): Promise<void> => {
        try {
            const response = await fetch('http://localhost:8080/info/get-user-wallet', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Userid': localStorage.getItem('username') || '', // Fallback to empty string if username is not set
                }
            });

            if (response.ok) {
                const data: WalletResponse = await response.json();
                localStorage.setItem('wallet-id', data.id);
            } else {
                const errorData: ErrorResponse = await response.json();
                setError(errorData.message); // Use the correct field for error message
            }
        } catch (err) {
            console.error(err);
            setError("An error occurred while fetching the wallet ID. Please try again.");
        }
    }

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>): Promise<void> => {
        e.preventDefault();
        try {
            const response = await fetch('http://localhost:8080/api-auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'username' : username,
                    'password' : password,
                },

            });

            console.log(response.status)

            if (response.status === 200) {
                const data: LoginResponse = await response.json();
                localStorage.setItem('authToken', data.token);
                localStorage.setItem('username', username);
                console.log(localStorage.getItem('authToken'));
                await getUserWalletId();
                console.log(localStorage.getItem("wallet-id"))// Fetch the wallet ID after successful login
                window.location.href = '/dashboard';
            } else {
                localStorage.clear();
                console.log("Im in Error Segment")
                // const errorData: ErrorResponse = await response.json();
                setError("An error occurred during login. Please try again.");
            }
        } catch (err) {
            console.error(err);
            setError("An error occurred during login. Please try again.");
        }
    }

    return (
        <div className="flex min-h-full flex-1 flex-col justify-center px-6 py-12 lg:px-8">
            <div className="sm:mx-auto sm:w-full sm:max-w-sm">
                <img
                    alt="Your Company"
                    src="src/assets/logo2.png"
                    className="mx-auto h-10 w-auto"
                />
                <h2 className="mt-10 text-center text-2xl font-bold leading-9 tracking-tight text-gray-900">
                    Sign in to your account
                </h2>
            </div>

            <div className="mt-10 sm:mx-auto sm:w-full sm:max-w-sm">
                <form onSubmit={handleSubmit} className="space-y-6">
                    <div>
                        <label htmlFor="email" className="block text-sm font-medium leading-6 text-gray-900">
                            Email address
                        </label>
                        <div className="mt-2">
                            <input
                                id="email"
                                name="email"
                                required
                                autoComplete="email"
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                            />
                        </div>
                    </div>

                    <div>
                        <div className="flex items-center justify-between">
                            <label htmlFor="password" className="block text-sm font-medium leading-6 text-gray-900">
                                Password
                            </label>
                        </div>
                        <div className="mt-2">
                            <input
                                id="password"
                                name="password"
                                type="password"
                                required
                                autoComplete="current-password"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                            />
                        </div>
                    </div>

                    <div>
                        <button
                            type="submit"
                            className="flex w-full justify-center rounded-md bg-indigo-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
                        >
                            Sign in
                        </button>
                    </div>
                </form>

                {error && <p className="mt-10 text-center text-sm text-red-500">{error}</p>}

                <p className="mt-10 text-center text-sm text-gray-500">
                    Not a member? {' '}
                    <a href="/register" className="font-semibold leading-6 text-indigo-600 hover:text-indigo-500">
                        Signup Now!
                    </a>
                </p>
            </div>
        </div>
    )
}