
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { Eye, EyeOff } from "lucide-react";
import api from "../services/api";

function Login() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const [errors, setErrors] = useState({});
  const [showPassword, setShowPassword] = useState(false);
  const [loading, setLoading] = useState(false);

  function handleChange(event) {
    const { name, value } = event.target;

    setForm((previous) => ({
      ...previous,
      [name]: value,
    }));

    setErrors((previous) => ({
      ...previous,
      [name]: "",
      form: "",
    }));
  }

  function validateForm() {
    const newErrors = {};
    const email = form.email.trim();

    if (!email) {
      newErrors.email = "Email is required.";
    } else if (email.length > 254) {
      newErrors.email = "Email cannot exceed 254 characters.";
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
      newErrors.email = "Enter a valid email address.";
    }

    if (!form.password) {
      newErrors.password = "Password is required.";
    }

    return newErrors;
  }

  async function handleSubmit(event) {
    event.preventDefault();

    if (loading) return;

    const validationErrors = validateForm();
    setErrors(validationErrors);

    if (Object.keys(validationErrors).length > 0) {
      return;
    }

    setLoading(true);

    try {
      const response = await api.post("/api/auth/login", {
        email: form.email.trim(),
        password: form.password,
      });

      const { token } = response.data;

      if (!token) {
        setErrors({
          form: "Login response did not contain an authentication token.",
        });
        return;
      }

      // Store the JWT for this browser tab's session.
      sessionStorage.setItem("authToken", token);
      // store the user's email
      sessionStorage.setItem("userEmail", response.data.email);

      // Navigate to the dashboard after successful authentication.
      navigate("/dashboard", { replace: true });
    } catch (error) {
      const status = error.response?.status;
      const responseData = error.response?.data;

      let message;

      if (!error.response) {
        message =
          "Unable to reach the server. Check that the backend is running and CORS is configured.";
      } else if (status === 401) {
        message = "Invalid email or password.";
      } else if (typeof responseData === "string") {
        message = responseData;
      } else {
        message =
          responseData?.message ||
          responseData?.error ||
          "Login failed. Please try again.";
      }

      setErrors({ form: message });
    } finally {
      setLoading(false);
    }
  }

  return (
    <main className="auth-page">
      <section className="auth-card">
        <h1>Welcome back</h1>
        <p>Sign in to continue to Resume Analyzer.</p>

        <form onSubmit={handleSubmit} noValidate>
          <label htmlFor="email">Email address</label>
          <input
            id="email"
            name="email"
            type="email"
            value={form.email}
            onChange={handleChange}
            placeholder="you@example.com"
            autoComplete="email"
            maxLength={254}
            aria-invalid={Boolean(errors.email)}
            aria-describedby={errors.email ? "email-error" : undefined}
          />

          {errors.email && (
            <span className="field-error" id="email-error">
              {errors.email}
            </span>
          )}

          <label htmlFor="password">Password</label>

          <div className="password-input-wrapper">
            <input
              id="password"
              name="password"
              type={showPassword ? "text" : "password"}
              value={form.password}
              onChange={handleChange}
              placeholder="Enter your password"
              autoComplete="current-password"
              aria-invalid={Boolean(errors.password)}
              aria-describedby={
                errors.password ? "password-error" : undefined
              }
            />

            <button
              type="button"
              className="password-toggle"
              onClick={() =>
                setShowPassword((previous) => !previous)
              }
              aria-label={
                showPassword ? "Hide password" : "Show password"
              }
            >
              {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
            </button>
          </div>

          {errors.password && (
            <span className="field-error" id="password-error">
              {errors.password}
            </span>
          )}

          <button
            className="button button-primary"
            type="submit"
            disabled={loading}
          >
            {loading ? "Signing in..." : "Sign In"}
          </button>

          {errors.form && (
            <p className="form-message form-error-message" role="alert">
              {errors.form}
            </p>
          )}
        </form>

        <p>
          Don't have an account? <Link to="/register">Create one</Link>
        </p>

        <Link to="/">Back to home</Link>
      </section>
    </main>
  );
}

export default Login;
