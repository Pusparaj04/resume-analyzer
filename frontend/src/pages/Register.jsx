import { useState } from "react";
import { Link } from "react-router-dom";
import { Eye, EyeOff } from "lucide-react";
import api from "../services/api";

function Register() {
  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [errors, setErrors] = useState({});
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);
  const [loading, setLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");

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

    setSuccessMessage("");
  }

  function validateForm() {
    const newErrors = {};
    const name = form.name.trim();
    const email = form.email.trim();

    if (!name) {
      newErrors.name = "Full name is required.";
    } else if (name.length < 2 || name.length > 50) {
      newErrors.name = "Name must contain 2–50 characters.";
    }

    if (!email) {
      newErrors.email = "Email is required.";
    } else if (email.length > 254) {
      newErrors.email = "Email cannot exceed 254 characters.";
    } else if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
      newErrors.email = "Enter a valid email address.";
    }

    if (!form.password) {
      newErrors.password = "Password is required.";
    } else if (
      form.password.length < 8 ||
      form.password.length > 13
    ) {
      newErrors.password = "Password must contain 8–13 characters.";
    }

    if (!form.confirmPassword) {
      newErrors.confirmPassword = "Please confirm your password.";
    } else if (form.password !== form.confirmPassword) {
      newErrors.confirmPassword = "Passwords do not match.";
    }

    return newErrors;
  }

  async function handleSubmit(event) {
    event.preventDefault();

    if (loading) return;

    const validationErrors = validateForm();
    setErrors(validationErrors);
    setSuccessMessage("");

    if (Object.keys(validationErrors).length > 0) {
      return;
    }

    setLoading(true);

    try {
      const response = await api.post("/api/auth/register", {
        name: form.name.trim(),
        email: form.email.trim(),
        password: form.password,
      });

      const message  = response.data;

      setSuccessMessage(
        message || "Registration successful. Your account has been created."
      );

      setForm({
        name: "",
        email: "",
        password: "",
        confirmPassword: "",
      });
    } catch (error) {
      const status = error.response?.status;
      const responseData = error.response?.data;

      let message;

      if (!error.response) {
        message =
          "Unable to reach the server. Check that the backend is running and CORS is configured.";
      } else if (status === 409) {
        message = "An account with this email may already exist.";
      } else if (typeof responseData === "string") {
        message = responseData;
      } else {
        message =
          responseData?.message ||
          responseData?.error ||
          "Registration failed. Please check your details and try again.";
      }

      setErrors({ form: message });
    } finally {
      setLoading(false);
    }
  }

  return (
    <main className="auth-page">
      <section className="auth-card">
        <h1>Create your account</h1>
        <p>Get started with your resume analysis.</p>

        <form onSubmit={handleSubmit} noValidate>
          <label htmlFor="name">Full name</label>
          <input
            id="name"
            name="name"
            type="text"
            value={form.name}
            onChange={handleChange}
            placeholder="Your full name"
            autoComplete="name"
            maxLength={50}
            aria-invalid={Boolean(errors.name)}
            aria-describedby={errors.name ? "name-error" : undefined}
          />
          {errors.name && (
            <span className="field-error" id="name-error">
              {errors.name}
            </span>
          )}

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
              placeholder="8–13 characters"
              autoComplete="new-password"
              minLength={8}
              maxLength={13}
              aria-invalid={Boolean(errors.password)}
              aria-describedby={
                errors.password ? "password-error" : undefined
              }
            />
            <button
              type="button"
              className="password-toggle"
              onClick={() => setShowPassword((previous) => !previous)}
              aria-label={showPassword ? "Hide password" : "Show password"}
            >
              {showPassword ? <EyeOff size={18} /> : <Eye size={18} />}
            </button>
          </div>
          {errors.password && (
            <span className="field-error" id="password-error">
              {errors.password}
            </span>
          )}

          <label htmlFor="confirmPassword">Confirm password</label>
          <div className="password-input-wrapper">
            <input
              id="confirmPassword"
              name="confirmPassword"
              type={showConfirmPassword ? "text" : "password"}
              value={form.confirmPassword}
              onChange={handleChange}
              placeholder="Re-enter your password"
              autoComplete="new-password"
              minLength={8}
              maxLength={13}
              aria-invalid={Boolean(errors.confirmPassword)}
              aria-describedby={
                errors.confirmPassword
                  ? "confirm-password-error"
                  : undefined
              }
            />
            <button
              type="button"
              className="password-toggle"
              onClick={() =>
                setShowConfirmPassword((previous) => !previous)
              }
              aria-label={
                showConfirmPassword ? "Hide password" : "Show password"
              }
            >
              {showConfirmPassword ? (
                <EyeOff size={18} />
              ) : (
                <Eye size={18} />
              )}
            </button>
          </div>
          {errors.confirmPassword && (
            <span className="field-error" id="confirm-password-error">
              {errors.confirmPassword}
            </span>
          )}

          <button
            className="button button-primary"
            type="submit"
            disabled={loading}
          >
            {loading ? "Creating account..." : "Create Account"}
          </button>

          {errors.form && (
            <p className="form-message form-error-message" role="alert">
              {errors.form}
            </p>
          )}

          {successMessage && (
            <p className="form-message form-success-message" role="status">
              {successMessage}
            </p>
          )}
        </form>

        <p>
          Already have an account? <Link to="/login">Sign in</Link>
        </p>
        <Link to="/">Back to home</Link>
      </section>
    </main>
  );
}

export default Register;