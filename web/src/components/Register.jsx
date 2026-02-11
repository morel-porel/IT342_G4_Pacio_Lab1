import React, {useState} from "react";
import { useForm } from "react-hook-form";
import { useNavigate, Link } from "react-router-dom";
import api from "../utils/api";
import "../App.css";

function Register() {
    const { 
        register, 
        handleSubmit,
        formState: { errors } 
    } = useForm();
    const navigate = useNavigate();
    const [serverError, setServerError] = useState("");
    const onSubmit = async(data) => {
        setServerError("");
        try {
            await api.post("/auth/register", {
                username: data.username,
                email: data.email,
                firstName: data.firstName,
                lastName: data.lastName,
                password: data.password
            });
            alert("Registration successful! Please log in.");
            navigate("/login");
        } catch (error) {
            if (error.response && error.response.status === 409) {
                setServerError("Username or Email already taken.");
            } else {
                const message = error.response?.data?.message || "Registration failed. Please try again.";
                setServerError(message);
            }
        }
    };

    return (
        <div className="App">
            <h2>Register</h2>
            <form onSubmit={handleSubmit(onSubmit)}>
                {serverError && <div className="error-message">{serverError}</div>}
                <input {...register("username", { required: true })} placeholder="Username" type="text"/>
                <input {...register("email", { required: true })} placeholder="Email" type="email" />
                <input {...register("firstName", { required: true })} placeholder="First Name" type="text" />
                <input {...register("lastName", { required: true })} placeholder="Last Name" type="text" />
                <input {...register("password", { required: true })} placeholder="Password" type="password" />
                <input type="submit" value="Submit" />
            </form>
            <p>Have an account? <Link to="/login">Login</Link></p>
        </div>
    );
}
export default Register;