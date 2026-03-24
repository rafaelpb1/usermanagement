import { useState } from "react";
import { AxiosError } from "axios";
import { login } from "./services/authService";
import "./App.css";

function App() {
  const [userLogin, setUserLogin] = useState("");
  const [password, setPassword] = useState("");
  const [message, setMessage] = useState("Pronto para testar POST /auth/login");

  async function testLogin() {
    setMessage("Enviando login para /auth/login...");

    try {
      const result = await login({ login: userLogin, password });
      localStorage.setItem("token", result.token);
      setMessage(`Login OK. Token salvo (expira em ${result.expiresIn}s).`);
    } catch (error) {
      if (error instanceof AxiosError) {
        const status = error.response?.status;
        const body = JSON.stringify(error.response?.data);
        setMessage(`Erro ${status ?? "sem status"}: ${body}`);
        return;
      }

      setMessage("Erro inesperado no teste de login.");
    }
  }

  return (
    <main style={{ maxWidth: 480, margin: "40px auto", fontFamily: "Arial" }}>
      <h1>Etapa 2 - Teste de Login</h1>

      <p>
        Este teste chama <code>POST /auth/login</code>.
      </p>

      <label htmlFor="login">Login</label>
      <input
        id="login"
        value={userLogin}
        onChange={(e) => setUserLogin(e.target.value)}
        placeholder="seu login"
        style={{ width: "100%", marginBottom: 12, padding: 8 }}
      />

      <label htmlFor="password">Senha</label>
      <input
        id="password"
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="sua senha"
        style={{ width: "100%", marginBottom: 12, padding: 8 }}
      />

      <button onClick={testLogin} style={{ padding: "10px 14px" }}>
        Testar login
      </button>

      <pre style={{ whiteSpace: "pre-wrap", marginTop: 16 }}>{message}</pre>
    </main>
  );
}

export default App;