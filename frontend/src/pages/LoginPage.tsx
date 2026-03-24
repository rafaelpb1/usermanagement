import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { saveToken } from "../services/session";

const LoginPage: React.FC = () => {
    const [login, setLogin] = useState<string>("");
    const [password, setPassword] = useState<string>("");

    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        try {
            const response = await fetch("/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({ login, password })
            });

            if (!response.ok) {
                throw new Error("Falha no login");
            }

            const result: { token: string } = await response.json();

            saveToken(result.token);
            navigate("/dashboard");

        } catch (error) {
            console.error("Erro no login:", error);
        }
    };

    return (
        <div className="min-h-screen flex items-center justify-center bg-gray-50 px-4">
            <div className="max-w-md w-full space-y-8 p-10 bg-white rounded-xl shadow-lg border border-gray-100">
                
                <div className="text-center">
                    <h2 className="mt-6 text-3xl font-extrabold text-gray-900">
                        Bem-vindo
                    </h2>
                    <p className="mt-2 text-sm text-gray-600">
                        Acesse sua conta para continuar
                    </p>
                </div>

                <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
                    <div className="rounded-md shadow-sm space-y-4">
                        
                        <div>
                            <label htmlFor="login" className="block text-sm font-medium text-gray-700">
                                Login
                            </label>
                            <input
                                type="text"
                                id="login"
                                required
                                className="appearance-none rounded-lg block w-full px-3 py-3 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                                placeholder="seuLogin"
                                value={login}
                                onChange={(e) => setLogin(e.target.value)}
                            />
                        </div>

                        <div>
                            <label htmlFor="password" className="block text-sm font-medium text-gray-700">
                                Senha
                            </label>
                            <input
                                type="password"
                                id="password"
                                required
                                className="appearance-none rounded-lg block w-full px-3 py-3 border border-gray-300 placeholder-gray-500 text-gray-900 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                                placeholder="••••••••"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                            />
                        </div>

                    </div>

                    <button
                        type="submit"
                        className="group relative w-full flex justify-center py-3 px-4 text-sm font-medium rounded-lg text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500 transition-colors duration-200"
                    >
                        Entrar
                    </button>
                </form>

            </div>
        </div>
    );
};

export default LoginPage;