import { createContext, useContext, useState, useEffect } from 'react';

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [jwt, setJwt] = useState(() => localStorage.getItem("jwt"));

  // Keep localStorage in sync with jwt
  useEffect(() => {
    if (jwt) {
      localStorage.setItem("jwt", jwt);
    } else {
      localStorage.removeItem("jwt");
    }
  }, [jwt]);

  return (
    <AuthContext.Provider value={{ jwt, setJwt }}>
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  return useContext(AuthContext);
}
