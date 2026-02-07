import React from "react";
import { useForm } from "react-hook-form";
import { useNavigate, Link } from "react-router-dom";
import { useAuth } from "../utils/AuthContext";
import api from "../utils/api";
import "../App.css";

function Login() {
    const { 
        register, 
        handleSubmit,
        formState: { errors }
    } = useForm();
    const navigate = useNavigate();
    const { login } = useAuth();

    const onSubmit = async(data) => {
        try {
            const response = await api.post("/auth/login", data);
            login(response.data.token, response.data.user);
            navigate("/dashboard");
        } catch (error) {
            alert(error.response?.data || "Invalid credentials");
        }
        
    };

    return (
        <div className="App">
            <h2>Login</h2>
            <form onSubmit={handleSubmit(onSubmit)}>
                <input {...register("username", { required: true })} placeholder="Username" type="text" />
                <input {...register("password", { required: true })} placeholder="Password" type="password" />
                <input type="submit" value="Login" />
            </form>
            <p>Don't have an account? <Link to="/register">Register</Link></p>
        </div>
    );

}
export default Login;