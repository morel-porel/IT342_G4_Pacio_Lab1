import React, {useState} from "react";
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
    const [loginError, setLoginError] = useState("");
    const onSubmit = async(data) => {
        setLoginError("");
        try {
            const response = await api.post("/auth/login", data);
            login(response.data.token, response.data.user);
            navigate("/dashboard");
        } catch (error) {
            const message = error.response?.data?.message || "Invalid credentials";
            setLoginError(message);
        }
        
    };

    return (
        <div className="App">
            <h2>Login</h2>
            <form onSubmit={handleSubmit(onSubmit)}>
                {loginError && <div className="error-message">{loginError}</div>}
                <input {...register("username", { required: true })} placeholder="Username" type="text" />
                <input {...register("password", { required: true })} placeholder="Password" type="password" />
                <input type="submit" value="Login" />
            </form>
            <p>Don't have an account? <Link to="/register">Register</Link></p>
        </div>
    );

}
export default Login;