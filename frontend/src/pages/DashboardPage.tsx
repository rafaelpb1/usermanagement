import React from "react";
import { useNavigate } from "react-router-dom";
import { clearToken } from "../services/session";

const DashboardPage: React.FC = () => {
  const navigate = useNavigate();

  const handleLogout = () => {
    clearToken();
    navigate("/login");
  };

  return (
    <div style={{ minHeight: "100vh", background: "#f3f4f6" }}>
      <nav
        style={{
          background: "linear-gradient(90deg, rgba(17,24,39,0.72) 0%, rgba(31,41,55,0.72) 100%)",
          color: "#ffffff",
          borderBottom: "1px solid rgba(255,255,255,0.14)",
          boxShadow: "0 10px 24px rgba(0,0,0,0.22)",
          backdropFilter: "blur(14px) saturate(140%)",
          WebkitBackdropFilter: "blur(14px) saturate(140%)",
        }}
      >
        <div
          style={{
            maxWidth: "1120px",
            margin: "0 auto",
            padding: "14px 20px",
            display: "flex",
            alignItems: "center",
            justifyContent: "space-between",
          }}
        >
          <div style={{ display: "flex", alignItems: "center", gap: 10 }}>
            <div
              style={{
                width: 34,
                height: 34,
                borderRadius: 8,
                background: "#4f46e5",
                display: "grid",
                placeItems: "center",
                fontWeight: 800,
              }}
            >
              SV
            </div>
            <div>
              <strong style={{ fontSize: 16, letterSpacing: 0.3 }}>Sistema de venda de veículos</strong>
              <p style={{ margin: 0, fontSize: 12, color: "#d1d5db" }}>Dashboard</p>
            </div>
          </div>

          <div style={{ display: "flex", alignItems: "center", gap: 18, fontSize: 14 }}>
            <a href="#" style={{ color: "#e5e7eb", textDecoration: "none" }}>Início</a>
            <a href="#" style={{ color: "#e5e7eb", textDecoration: "none" }}>Estoque</a>
            <a href="#" style={{ color: "#e5e7eb", textDecoration: "none" }}>Vendas</a>
            <a href="#" style={{ color: "#e5e7eb", textDecoration: "none" }}>Clientes</a>
            <button
              onClick={handleLogout}
              style={{
                border: "none",
                borderRadius: 8,
                padding: "8px 12px",
                background: "#dc2626",
                color: "#ffffff",
                fontWeight: 600,
                cursor: "pointer",
              }}
            >
              Sair
            </button>
          </div>
        </div>
      </nav>
    </div>
  );
};

export default DashboardPage;
