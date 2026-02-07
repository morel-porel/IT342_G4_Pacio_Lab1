import React, { useEffect } from "react";
import { useAuth } from "../utils/AuthContext";
import api from "../utils/api";
import "../App.css";

function Profile() {
    const { user, setUser } = useAuth();
    useEffect(() => {
        api.get("/auth/me")
        .then(res=> setUser(res.data))
        .catch(err=> console.log("Session expired, please login again."));
    },[]);
    if (!user) return <p>Loading profile...</p>;
    return (
        <div>
            <h3>Your Profile Information:</h3>
                <p><strong>Username:</strong> {user.username}</p>
                <p><strong>Email:</strong> {user.email}</p>
                <p><strong>First Name:</strong> {user.firstName}</p>
                <p><strong>Last Name:</strong> {user.lastName}</p>
        </div>
    );
}
export default Profile;