
import { useNavigate } from "react-router-dom";
import {
  FileText,
  FileCheck,
  ChartNoAxesCombined,
  Upload,
  LogOut,
  UserRound,
} from "lucide-react";

function Dashboard() {
  const navigate = useNavigate();

  const email = sessionStorage.getItem("userEmail") || "User";

  function handleLogout() {
    sessionStorage.removeItem("authToken");
    sessionStorage.removeItem("userEmail");

    navigate("/login", { replace: true });
  }

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <div className="dashboard-brand">
          <FileText size={25} />
          <span>
            Resume<span className="brand-accent">Analyzer</span>
          </span>
        </div>

        <div className="dashboard-user">
          <UserRound size={19} />
          <span>{email}</span>

          <button onClick={handleLogout} className="logout-button">
            <LogOut size={17} />
            Logout
          </button>
        </div>
      </header>

      <main className="dashboard-content">
        <section className="dashboard-welcome">
          <h1>Welcome back!</h1>
          <p>
            Analyze your resume, identify skill gaps, and improve
            your chances of getting shortlisted.
          </p>
        </section>

        <section className="dashboard-stats">
          <div className="stat-card">
            <FileCheck size={25} />
            <p>Resumes Analyzed</p>
            <h2>0</h2>
          </div>

          <div className="stat-card">
            <ChartNoAxesCombined size={25} />
            <p>Analyses Completed</p>
            <h2>0</h2>
          </div>
        </section>

        <section className="analysis-section">
          <div className="section-heading">
            <h2>Start a New Analysis</h2>
            <p>
              Upload your resume and provide a job description
              to get an AI-powered evaluation.
            </p>
          </div>

          <div className="upload-placeholder">
            <Upload size={42} />
            <h3>Resume Analysis Workspace</h3>
            <p>
              Your resume upload and job matching workspace
              will be available here.
            </p>
          </div>
        </section>
      </main>
    </div>
  );
}

export default Dashboard;
