import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Navbar from './Navbar.tsx';
import Login from './Components/Login';
import Register from './Components/Register';
import Buy from './Components/Buy';
import Sell from './Components/Sell';
import Transfer from './Components/Transfer';
import Profile from './Components/Profile';
import Dashboard from './Components/Dashboard';
import './index.css';
import AddUsd from "./Components/AddUsd.tsx";


const App: React.FC = () => {
    const isLoggedIn = !!localStorage.getItem('authToken');


    return (
        <Router>
            {isLoggedIn && <Navbar />}
            <Routes>
                <Route path="/login" element={isLoggedIn ? <Navigate to="/dashboard" /> : <Login />} />
                <Route path="/register" element={isLoggedIn ? <Navigate to="/dashboard" /> : <Register />} />

                {isLoggedIn ? (
                    <>
                        <Route path="/buy" element={<Buy />} />
                        <Route path="/sell" element={<Sell />} />
                        <Route path="/transfer" element={<Transfer />} />
                        <Route path="/dashboard" element={<Dashboard />} />
                        <Route path="/add-usd" element={<AddUsd />} />
                        <Route path="/home" element={<App />} />
                    </>
                ) : (
                    <Route path="*" element={<Navigate to="/login" />} />
                )}
                <Route path="/" element={<Login />} />
            </Routes>
        </Router>
    );
}


export default App;
