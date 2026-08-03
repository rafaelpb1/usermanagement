export type LoginRequest = {
    login: string;
    email: string;
    password: string;
};

export type LoginResponse = {
    token: string;
    expiresIn: number;
};