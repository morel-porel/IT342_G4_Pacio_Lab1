import Profile from "../components/Profile";
import { useAuth } from "../utils/AuthContext";
import { useNavigate } from "react-router-dom";
import "../App.css";

function Dashboard() {
    const { user, logout } = useAuth();
    const navigate = useNavigate();
    const handleLogout = () => {
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