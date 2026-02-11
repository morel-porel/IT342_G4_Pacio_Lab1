import Profile from "../components/Profile";
import { useAuth } from "../utils/AuthContext";
import { useNavigate } from "react-router-dom";
import api from "../utils/api";
import "../App.css";

function Dashboard() {
    const { user, logout } = useAuth();
    const navigate = useNavigate();
    const handleLogout = async () => {
        try {
            await api.post("/auth/logout");
        } catch (error) {
            console.error("Logout API call failed", error);
        }
        logout();
        alert("You have been logged out successfully.");
        navigate("/login");
    }
    return (
        <div className="App">
            <h1>Welcome to the Dashboard, {user?.username}!</h1>
            <Profile />
            <button onClick={handleLogout}>Logout</button>
        </div>
    );
}
export default Dashboard;