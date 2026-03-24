import { api } from "./api";
import type { LoginRequest, LoginResponse } from "../types/auth";

const LOGIN_PATH = "/auth/login";

export async function login(data: LoginRequest): Promise<LoginResponse> {
  const response = await api.post<LoginResponse>(LOGIN_PATH, data);
  return response.data;
}