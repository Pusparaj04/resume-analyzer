import { 
  ArrowDown,
  ArrowRight,
  BrainCircuit,
  CheckCircle2,
  FileText,
  Lightbulb,
  Search,
  ShieldCheck,
  Sparkles,
  Star,
  TrendingUp,
  Upload,
  XCircle,
  AlertCircle,
  BarChart3,
 } from "lucide-react";
 import{useEffect, useState} from "react";
 import "./App.css";

 const features = [
  {
    title: "ATS Scoring",
    description: "Understand how well your resume matches job requirements.",
    icon: TrendingUp,
    color: "green",
  },
  {
    title: "Skill Gap Analysis",
    description: "Identify missing skills and areas for improvement.",
    icon: Search,
    color: "purple",
  },
  {
    title: "AI-Powered Insights",
    description: "Discover strengths, weakness, and personalized suggestions.",
    icon: Sparkles,
    color: "orange",
  },
  {
    title: "Secure & Private",
    description: "Access your account through secure authentication.",
    icon: ShieldCheck,
    color: "blue",
  },
 ];

 const steps = [
  {
    number: "1",
    title: "Upload Resume",
    description: "Upload you resume in PDF format.",
    icon: Upload,
    color: "blue",
  },
  {
    number: "2",
    title: "Add Job Description",
    description: "Paste the job description you want to apply for.",
    icon: FileText,
    color: "purple",
  },
  {
    number: "3",
    title: "AI Analysis",
    description: "Let AI evaluate your resume against the job requirements.",
    icon: BrainCircuit,
    color: "green",
  },
  {
    number: "4",
    title: "Get Your Report",
    description: "Review your score, skill gaps, and improvement suggestions.",
    icon: BarChart3,
    color: "orange",
  },
 ];

 function Navbar(){
  const [activeSection, setActiveSection] = useState("home");

  useEffect(() => {
    const sectionIds = ["home", "features", "how-it-works"];

    const observer = new IntersectionObserver(
      (entries) => {
        const visibleSections = entries
        .filter((entry) => entry.isIntersecting)
        .sort(
          (a, b) =>
            b.intersectionRatio - a.intersectionRatio
        );

        if(visibleSections.length > 0){
          setActiveSection(visibleSections[0].target.id);
        }
      },
      {
        rootMargin : "-15% 0px -65% 0px",
        threshold: 0,
      }
    );

    sectionIds.forEach((id) => {
      const section = document.getElementById(id);
      if(section) observer.observe(section);
    });

    return () => observer.disconnect();
  }, []);

  const navLinls = [
    {label: "Home", id: "home"},
    {label: "Features", id: "features"},
    {label: "How It Works", id: "how-it-works"},
  ];

  return (
    <header className="navbar">
      <a className="brand" href="#home" aria-label="Resume Analyzer home">
        <span className="brand-icon">
          <FileText size={24} />
        </span>
        <span>Resume <span className="brand-accent"> Analyzer</span></span>
      </a>

      <nav className="nav-links" aria-label="Main navigation">
        {navLinls.map(({ label, id}) => (
          <a
            key={id}
            href={`#${id}`}
            className={activeSection === id ? "nav-active" : ""}
            aria-current={activeSection === id ? "location" : undefined}
            onClick={() => setActiveSection(id)}
          >
            {label}
            </a>
        ))}
      </nav>

      <div className="nav-actions">
        <a className="button button-outline button-small" href="#get-started">
          Login
        </a>
        <a className="button button-primary button-small" href="#get-started">
          Get Started
        </a>
      </div>
    </header>
  );
 }

 function AnalysisPreview() {
  return (
    <div className="preview-scene" aria-label="Illustration of a resume analysis report">
      <div className="preview-glow" />

      <div className="floating-document resume-docum">
        <div className="document-heading">
          <FileText size={19} />
          <span>My Resume.pdf</span>
        </div>
        <div className="document-line long" />
        <div className="document-line medium" />
        <div className="document-line long" />
        <div className="document-line short" />
        <div className="document-line medium" />
        <span className="pdf-tag">PDF</span>
      </div>

      <div className="analysis-card">
        <div className="analysis-card-header">
          <span className="mini-brand">
            <Sparkles size={17} />
          </span>
          <div>
            <strong>Analysis Preview</strong>
            <span>AI-powered resume insights</span>
          </div>
        </div>

        <div className="score-area">
          <div className="score-ring">
            <div className="score-ring-inner">
              <strong>85</strong>
              <span>ATS score</span>
            </div>
          </div>
          <div className="score-label">
            <span>Strong Match</span>
            <div className="score-progress">
              <div />
            </div>
            <small>Illustrative score</small>
          </div>
        </div>

        <div className="preview-divider" />

        <div className="preview-result">
          <CheckCircle2 className="result-success" size={18} />
          <span>Strengths</span>
          <span className="result-count">04</span>
        </div>
        <div className="preview-result">
          <XCircle className="result-danger" size={18} />
          <span>Area to improve</span>
          <span className="result-count">03</span>
        </div>
        <div className="preview-result">
          <AlertCircle className="result-warning" size={18} />
          <span>Missing skills</span>
          <span className="result-count">05</span>
        </div>
      </div>

      <div className="floating-tip">
        <span className="tip-icon"><Lightbulb size={25} /></span>
        <div>
          <strong>Actionable insights</strong>
          <span>Know what to improve next</span>
        </div>
      </div>

      <div className="floating-sparkle sparkle-one"><Sparkles size={23} /></div>
      <div className="floating-sparkle sparkle-two"><Star size={18} /></div>
    </div>
  );
 }

function Hero() {
  return (
    <section className="hero" id="home">
      <div className="hero-content">
        <div className="eyebrow">
          <span>AI-POWERED RESUME ANALYZER</span>
        </div>

        <h1>
          Make Your Resume
          <br />
          <span className="gradient-text">Stand Out.</span>
        </h1>

        <p className="hero-description">
          Get acctionable insights into your resume. Analyze your skills,
          identify skill gaps, and discover how well your experience aligns
          with your dream job.
        </p>

        <div className="hero-actions">
          <a className="button button-primary button-large" href="#get-started">
            <Upload size={19} />
            Analyze Your Resume
            <ArrowRight size={18} />
          </a>
          <a className="button button-text button-large" href="#how-it-works">
            See How It Works
            <ArrowDown size={17} />
          </a>
        </div>

        <div className="hero-trust">
          <span><ShieldCheck size={17} /> Secure authentication</span>
          <span><FileText size={17} /> PDF resume support</span>
        </div>
      </div>

      <div className="hero-visual">
        <AnalysisPreview />
      </div>
    </section>
  ); 
}

function Features() {
  return (
    <section className="features section-container" id="features">
      <div className="section-heading">
        <span className="section-kicker"> BUILT FOR YOUR NEXT MOVE</span>
        <h2>Everything you need to improve your resume</h2>
        <p>Turn your resume into a stronger application with focused feedback.</p>
      </div>

      <div className="feature-grid">
        {features.map(({title, description, icon: Icon, color }) => (
          <article className="feature-card" key={title}>
            <div className={`icon-box ${color}`}>
              <Icon size={23} />
            </div>
            <h3>{title}</h3>
            <p>{description}</p>
          </article>
        ))}
      </div>
    </section>
  );  
}

function HowItWorks() {
  return (
    <section className="how-section" id="how-it-works">
      <div className="section-container">
        <div className="section-heading">
          <span className="section-kicker">A SIMPLE FOUR-STEP PROCESS</span>
          <h2>From resume to actionable insights</h2>
          <p>Get started in minutes with a straightforward workflow.</p>
        </div>

        <div className="steps-grid">
          {steps.map(({ number, title, description, icon: Icon, color}, index) => (
            <article className="step-card" key={number}>
              <div className={`step-icon ${color}`}>
                <Icon size={25} />
                <span className="step-number">{number}</span>
              </div>
              {index < steps.length - 1 && (
                <ArrowRight className="step-arrow" size={22} />
              )}
              <h3>{title}</h3>
              <p>{description}</p>
            </article>
          ))}
        </div>
      </div>
    </section>
  );
}

function CallToAction() {
  return (
    <section className="cta-section section-container" id="get-started">
      <div className="cta-card">
        <div className="cta-icon"><Sparkles size={26} /></div>
        <h2>Ready to strengthen your next application?</h2>
        <p>Start with your resume and a job description. Let the analysis guide
          your next steps.
        </p>
        <a className="button button-light button-large" href="#home">
          Get Started <ArrowRight size={18} />
        </a>
      </div>
    </section>
  );
}

function Footer() {
  return (
    <footer className="footer">
      <a className="brand footer-brand" href="#home">
        <span className="brand-icon"><FileText size={21} /></span>
        <span>Resume<span className="brand-accent">Analyzer</span></span>
      </a>
      <p>Build a stronger resume. Take next step in your career.</p>
      <span className="copyright">© {new Date().getFullYear()} Resume Analyzer</span>
    </footer>
  );
}

function App() {
  return (
    <>
      <Navbar />
      <main>
        <Hero />
        <Features />
        <HowItWorks />
        <CallToAction />
      </main>
      <Footer />
    </>
  );
}

export default App;
