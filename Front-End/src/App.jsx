import "./App.css"; // Importing App.css
import React, { useEffect, useState } from "react"; // Import React
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom'; // Importing BrowserRouter
import { getAuth, setPersistence, browserSessionPersistence, onAuthStateChanged } from "firebase/auth"; // Importing Firebase Auth
import { app } from "./Server/FirebaseConfig"; // Importing FirebaseConfig

// Importing componentes
/* import Login from "./Components/Pages/Login/Login"; // Importing Login */
import Register from "./Components/Pages/Login/Register";
import Home from "./Components/Pages/Home/Home"; // Importing Home

import { AuthGoogleProvider } from "./Contexts/AuthGoogle"; // Importing AuthGoogleProvider

function App() {
    const [loading, setLoading] = useState(true);
    const [authenticated, setAuthenticated] = useState(false);

    useEffect(() => {
        const auth = getAuth(app); // Create a constant called auth
        setPersistence(auth, browserSessionPersistence) // Set persistence to session
            .then(() => {
                setLoading(false);
            })
            .catch((error) => {
                console.error("Error setting persistence: ", error);
                setLoading(false);
            });

        const unsubscribe = onAuthStateChanged(auth, (user) => {
            if (!loading) {
                setAuthenticated(!!user);
            }
        });

        return () => unsubscribe();
    }, [loading]);

    if (loading) {
        return <div>Loading...</div>;
    }

    return (
        <div className="App">
            <Router>
                <AuthGoogleProvider>
                    <Routes>
                        <Route path="/" element={<Home />} />
                        <Route path="/Register" element={<Register />} />
                        <Route path="/Home" element={<Home />} />
                        <Route
                            path="/"
                            element={authenticated ? <Navigate to="/Home" /> : <Navigate to="/Login" />}
                        />
                    </Routes>
                </AuthGoogleProvider>
            </Router>
        </div>
    );
}

export default App; // Exporting App component