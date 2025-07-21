import { AuthProvider } from './AuthContext';
import { Routes, Route } from 'react-router-dom';
import LoginForm from './components/LoginForm';
import UserPage from './components/UserPage';
import CreateAccountForm from './components/CreateAccountForm';
import AccountsPage from './components/AccountsPage';
import UpdateUserForm from './components/UpdateUserForm';
import RegisterForm from './components/RegisterForm';

export default function App() {
  return (
    <AuthProvider>
      <Routes>
        <Route path="/" element={<LoginForm />} />
        <Route path="/register" element={<RegisterForm />} />
        <Route path="/user/:userId" element={<UserPage />} />
        <Route path="/user/:userId/accounts" element={<AccountsPage />} />
        <Route path="/user/:userId/update" element={<UpdateUserForm />} />
        <Route path="/user/:userId/accounts/create" element={<CreateAccountForm />} />
      </Routes>
    </AuthProvider>
  );
}
