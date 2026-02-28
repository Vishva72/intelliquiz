import { Link } from "react-router-dom";
import "./landing.css";

export default function Landing() {
  return (
    <div className="landing">

      {/* NAVBAR */}
      <header className="navbar">
        <div className="container nav">

          <div className="logo">AssessAI</div>

          <nav>
            <a href="#features">Features</a>
            <a href="#workflow">Workflow</a>

            <Link to="/login" className="btn-outline">
              Login
            </Link>

            <Link to="/admin-signup" className="btn-primary">
              Admin Signup
            </Link>
          </nav>

        </div>
      </header>


      {/* HERO */}
      <section className="hero">
        <div className="container hero-grid">

          <div className="hero-text">

            <h1>
              Smart Online Quiz Platform with
              <span> Automated Evaluation</span>
            </h1>

            <p>
              Create quizzes, invite participants, schedule exams,
              evaluate automatically, and publish results instantly —
              all with secure role-based access and intelligent backend automation.
            </p>

            <div className="hero-buttons">
              <Link to="/admin-signup" className="btn-primary">
                Create Admin Account
              </Link>

              <Link to="/login" className="btn-outline">
                Login
              </Link>
            </div>

            <div className="note">
              Participant accounts are automatically generated when invited by an administrator.
            </div>

          </div>

          <div className="hero-image">
            <img src="/images/hero.svg" alt="Quiz Platform" />
          </div>

        </div>
      </section>


      {/* FEATURES */}
      <section id="features" className="section">
        <div className="container">

          <h2>Platform Features</h2>

          <div className="grid">

            <div className="card">
              <h3>Secure Authentication</h3>
              <p>
                JWT-based authentication with role-based access control for admins and participants.
              </p>
            </div>

            <div className="card">
              <h3>Quiz Lifecycle Automation</h3>
              <p>
                Automatic transitions from Draft → Scheduled → Live → Closed using backend scheduler.
              </p>
            </div>

            <div className="card">
              <h3>Automatic Evaluation</h3>
              <p>
                Instant score calculation and submission tracking with result publishing control.
              </p>
            </div>

            <div className="card">
              <h3>Email Invitations</h3>
              <p>
                Participants receive credentials and invitations automatically via email.
              </p>
            </div>

            <div className="card">
              <h3>Role-Based Dashboards</h3>
              <p>
                Dedicated dashboards for administrators and participants with optimized workflows.
              </p>
            </div>

            <div className="card">
              <h3>Scalable Architecture</h3>
              <p>
                Built using Spring Boot, JWT security, scheduler automation, and modern React frontend.
              </p>
            </div>

          </div>

        </div>
      </section>


      {/* WORKFLOW */}
      <section id="workflow" className="section alt">
        <div className="container">

          <h2>How It Works</h2>

          <div className="steps">

            <div className="step">
              <span>1</span>
              <h4>Create Quiz</h4>
              <p>Admin creates quiz and adds questions.</p>
            </div>

            <div className="step">
              <span>2</span>
              <h4>Invite Participants</h4>
              <p>System generates participant accounts and sends credentials.</p>
            </div>

            <div className="step">
              <span>3</span>
              <h4>Schedule</h4>
              <p>Quiz becomes live automatically at scheduled time.</p>
            </div>

            <div className="step">
              <span>4</span>
              <h4>Attempt Quiz</h4>
              <p>Participants attempt within the allowed window.</p>
            </div>

            <div className="step">
              <span>5</span>
              <h4>Results</h4>
              <p>Scores calculated and published instantly.</p>
            </div>

          </div>

        </div>
      </section>


      {/* ROLES */}
      <section className="section">
        <div className="container">

          <h2>User Roles</h2>

          <div className="grid">

            <div className="card">
              <h3>Administrator</h3>
              <ul>
                <li>Create and manage quizzes</li>
                <li>Add questions manually or via AI</li>
                <li>Invite participants</li>
                <li>Schedule quizzes</li>
                <li>Publish results</li>
              </ul>
            </div>

            <div className="card">
              <h3>Participant</h3>
              <ul>
                <li>Receive system-generated account</li>
                <li>View invited quizzes</li>
                <li>Attempt quizzes within time window</li>
                <li>Submit answers securely</li>
                <li>View results after publishing</li>
              </ul>
            </div>

          </div>

        </div>
      </section>


      {/* CTA */}
      <section className="cta">
        <div className="container">

          <h2>Ready to Build Smart Assessments?</h2>

          <p>
            Start by creating your administrator account and begin managing quizzes today.
          </p>

          <Link to="/admin-signup" className="btn-primary">
            Create Admin Account
          </Link>

        </div>
      </section>


      {/* FOOTER */}
      <footer className="footer">
        <div className="container">
          © 2026 AssessAI — Intelligent Assessment Platform
        </div>
      </footer>

    </div>
  );
}