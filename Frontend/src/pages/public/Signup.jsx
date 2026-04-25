import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import Navbar from '../../components/layout/Navbar';
import Footer from '../../components/layout/Footer';
import { useAuth } from '../../context/AuthContext';

const Signup = () => {
    const navigate = useNavigate();
    const { login, registerWithEmail, dashboardPath } = useAuth();
    const [form, setForm] = useState({
        name: '',
        email: '',
        password: '',
        confirmPassword: '',
    });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const handleChange = (event) => {
        const { name, value } = event.target;
        setForm((prev) => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setError('');

        if (form.password.length < 6) {
            setError('Password must be at least 6 characters long.');
            return;
        }

        if (form.password !== form.confirmPassword) {
            setError('Passwords do not match.');
            return;
        }

        setLoading(true);

        try {
            await registerWithEmail({
                name: form.name,
                email: form.email,
                password: form.password,
            });
            navigate(dashboardPath || '/dashboard', { replace: true });
        } catch (err) {
            setError(err.message || 'Unable to sign up.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen flex flex-col bg-gray-50">
            <Navbar />

            <main className="flex-grow flex items-center justify-center py-20 px-4 sm:px-6 lg:px-8 mt-16">
                <div className="max-w-md w-full space-y-8 bg-white p-10 rounded-2xl shadow-xl border border-gray-100">
                    <div className="text-center">
                        <div className="mx-auto h-12 w-12 text-yellow-500 bg-yellow-50 rounded-full flex items-center justify-center mb-4">
                            <svg className="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M18 9v6m3-3h-6m-2 5a5 5 0 01-10 0V7a5 5 0 0110 0v10z" />
                            </svg>
                        </div>
                        <h2 className="mt-2 text-3xl font-extrabold text-gray-900">
                            Sign Up
                        </h2>
                        <p className="mt-2 text-sm text-gray-600">
                            Create your Smart Campus account with email and password
                        </p>
                    </div>

                    <div className="mt-8 space-y-6">
                        <form className="space-y-4" onSubmit={handleSubmit}>
                            <div>
                                <label htmlFor="name" className="block text-sm font-medium text-gray-700 mb-1">
                                    Full Name
                                </label>
                                <input
                                    id="name"
                                    name="name"
                                    type="text"
                                    required
                                    value={form.name}
                                    onChange={handleChange}
                                    className="w-full rounded-xl border border-gray-300 px-4 py-3 text-sm focus:border-yellow-500 focus:outline-none focus:ring-2 focus:ring-yellow-200"
                                    placeholder="Your full name"
                                />
                            </div>

                            <div>
                                <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-1">
                                    Email
                                </label>
                                <input
                                    id="email"
                                    name="email"
                                    type="email"
                                    required
                                    value={form.email}
                                    onChange={handleChange}
                                    className="w-full rounded-xl border border-gray-300 px-4 py-3 text-sm focus:border-yellow-500 focus:outline-none focus:ring-2 focus:ring-yellow-200"
                                    placeholder="you@example.com"
                                />
                            </div>

                            <div>
                                <label htmlFor="password" className="block text-sm font-medium text-gray-700 mb-1">
                                    Password
                                </label>
                                <input
                                    id="password"
                                    name="password"
                                    type="password"
                                    required
                                    value={form.password}
                                    onChange={handleChange}
                                    className="w-full rounded-xl border border-gray-300 px-4 py-3 text-sm focus:border-yellow-500 focus:outline-none focus:ring-2 focus:ring-yellow-200"
                                    placeholder="At least 6 characters"
                                />
                            </div>

                            <div>
                                <label htmlFor="confirmPassword" className="block text-sm font-medium text-gray-700 mb-1">
                                    Confirm Password
                                </label>
                                <input
                                    id="confirmPassword"
                                    name="confirmPassword"
                                    type="password"
                                    required
                                    value={form.confirmPassword}
                                    onChange={handleChange}
                                    className="w-full rounded-xl border border-gray-300 px-4 py-3 text-sm focus:border-yellow-500 focus:outline-none focus:ring-2 focus:ring-yellow-200"
                                    placeholder="Re-enter your password"
                                />
                            </div>

                            {error && (
                                <p className="rounded-lg bg-red-50 px-3 py-2 text-sm text-red-600">
                                    {error}
                                </p>
                            )}

                            <button
                                type="submit"
                                disabled={loading}
                                className="w-full rounded-full bg-yellow-500 px-4 py-3 text-sm font-semibold text-white transition hover:bg-yellow-600 disabled:cursor-not-allowed disabled:opacity-70"
                            >
                                {loading ? 'Creating Account...' : 'Create Account'}
                            </button>
                        </form>

                        <div className="relative">
                            <div className="absolute inset-0 flex items-center" aria-hidden="true">
                                <div className="w-full border-t border-gray-200" />
                            </div>
                            <div className="relative flex justify-center">
                                <span className="bg-white px-2 text-xs font-semibold uppercase tracking-wide text-gray-500">or</span>
                            </div>
                        </div>

                        <div className="flex flex-col items-center justify-center">
                            <button
                                type="button"
                                onClick={() => login('signup')}
                                className="group relative w-full flex justify-center items-center gap-3 py-3 px-4 border border-gray-300 text-sm font-semibold rounded-full text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-yellow-500 transition-all duration-300 shadow-sm hover:shadow"
                            >
                                <svg className="w-5 h-5" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M22.56 12.25c0-.78-.07-1.53-.2-2.25H12v4.26h5.92c-.26 1.37-1.04 2.53-2.21 3.31v2.77h3.57c2.08-1.92 3.28-4.74 3.28-8.09z" fill="#4285F4"/>
                                    <path d="M12 23c2.97 0 5.46-.98 7.28-2.66l-3.57-2.77c-.98.66-2.23 1.06-3.71 1.06-2.86 0-5.29-1.93-6.16-4.53H2.18v2.84C3.99 20.53 7.7 23 12 23z" fill="#34A853"/>
                                    <path d="M5.84 14.09c-.22-.66-.35-1.36-.35-2.09s.13-1.43.35-2.09V7.07H2.18C1.43 8.55 1 10.22 1 12s.43 3.45 1.18 4.93l2.85-2.22.81-.62z" fill="#FBBC05"/>
                                    <path d="M12 5.38c1.62 0 3.06.56 4.21 1.64l3.15-3.15C17.45 2.09 14.97 1 12 1 7.7 1 3.99 3.47 2.18 7.07l3.66 2.84c.87-2.6 3.3-4.53 6.16-4.53z" fill="#EA4335"/>
                                </svg>
                                Sign up with Google
                            </button>

                            <p className="mt-4 text-sm text-gray-600">
                                Already have an account?{' '}
                                <Link to="/login" className="font-semibold text-yellow-600 hover:text-yellow-700">
                                    Sign in
                                </Link>
                            </p>
                        </div>
                    </div>
                </div>
            </main>

            <Footer />
        </div>
    );
};

export default Signup;
