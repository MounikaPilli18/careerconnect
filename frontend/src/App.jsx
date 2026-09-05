import { useState } from 'react'
import './App.css'
import Login from './pages/Login'
import StudentRegister from './pages/StudentRegister'
import CompanyDashboard from './pages/CompanyDashboard'
import PostJob from './pages/PostJob'

function App() {
  const [page, setPage] = useState('home')
  const [user, setUser] = useState(null)

  const handleLogin = (loginData) => {
    setUser(loginData)

    // Save credentials so the dashboard can call the backend.
    if (loginData.username) {
      localStorage.setItem('username', loginData.username)
    }

    if (loginData.password) {
      localStorage.setItem('password', loginData.password)
    }

    setPage('dashboard')
  }

  const handleLogout = () => {
    setUser(null)
    localStorage.removeItem('username')
    localStorage.removeItem('password')
    setPage('home')
  }

  if (page === 'login') {
    return (
      <Login
        onLogin={handleLogin}
        onBack={() => setPage('home')}
      />
    )
  }
  if (page === 'student-register') {
    return (
      <StudentRegister
        onBack={() => setPage('home')}
        onRegistered={() => setPage('login')}
      />
    )
  }

  if (page === 'dashboard' && user) {
    return (
      <CompanyDashboard
        user={user}
        onPostJob={() => setPage('post-job')}
        onLogout={handleLogout}
      />
    )
  }

  if (page === 'post-job' && user) {
    return (
      <PostJob
        user={user}
        onBack={() => setPage('dashboard')}
      />
    )
  }

  return (
    <div className="app">
      <nav className="navbar">
        <div className="logo">CareerConnect</div>

        <div className="nav-links">
          <a href="#jobs">Jobs</a>
          <a href="#about">About</a>

          <button
            className="login-btn"
            onClick={() => setPage('login')}
          >
            Login
          </button>
        </div>
      </nav>

      <main>
        ```jsx
<section className="hero-section">
  <div className="hero-content">
    <p className="tagline">
      YOUR CAREER. YOUR FUTURE.
    </p>

    <h1>
      Connect with your
      <span> dream career.</span>
    </h1>

    <p className="hero-text">
      CareerConnect brings students and companies
      together. Discover opportunities, showcase your
      skills, and take the next step in your career.
    </p>

    <div className="hero-buttons">
      <button
        className="primary-btn"
        onClick={() => {
          document
            .getElementById('jobs')
            ?.scrollIntoView()
        }}
      >
        Find Jobs
      </button>

      <button
        className="secondary-btn"
        onClick={() => setPage('student-register')}
      >
        Register as Student
      </button>

      <button
        className="secondary-btn"
        onClick={() => setPage('login')}
      >
        Register as Company
      </button>
    </div>
  </div>

  <div className="hero-card">
    <div className="card-icon">💼</div>

    <h2>Find your opportunity</h2>

    <p>
      Explore jobs from companies looking for talented
      students and professionals.
    </p>

    <div className="stats">
      <div>
        <strong>100+</strong>
        <small>Jobs</small>
      </div>

      <div>
        <strong>50+</strong>
        <small>Companies</small>
      </div>

      <div>
        <strong>1K+</strong>
        <small>Students</small>
      </div>
    </div>
  </div>
</section>
```

        <section id="jobs" className="jobs-placeholder">
          <h2>Career opportunities</h2>

          <p>
            Job search will be connected to the CareerConnect
            backend next.
          </p>
        </section>
      </main>
    </div>
  )
}

export default App