import { useState } from 'react'
import './App.css'

import Login from './pages/Login'
import StudentRegister from './pages/StudentRegister'
import StudentDashboard from './pages/StudentDashboard'
import CompanyDashboard from './pages/CompanyDashboard'
import PostJob from './pages/PostJob'

function App() {
  const [page, setPage] = useState('home')
  const [user, setUser] = useState(null)

  const handleLogin = (loginData) => {
    setUser(loginData)

    localStorage.setItem('username', loginData.username)
    localStorage.setItem('password', loginData.password)
    localStorage.setItem('role', loginData.role)

    if (loginData.role === 'STUDENT') {
      setPage('student-dashboard')
    } else {
      setPage('dashboard')
    }
  }

  const handleLogout = () => {
    setUser(null)

    localStorage.removeItem('username')
    localStorage.removeItem('password')
    localStorage.removeItem('role')

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

  if (page === 'student-dashboard' && user) {
    return (
      <StudentDashboard
        user={user}
        onLogout={handleLogout}
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

      <header className="navbar">
        <div className="logo">
          CareerConnect
        </div>

        <button
          className="nav-login"
          onClick={() => setPage('login')}
        >
          Login
        </button>
      </header>

      <main className="hero">

        <div className="hero-content">

          <p className="hero-label">
            CAREERCONNECT
          </p>

          <h1>
            Connect your skills
            <br />
            with your future.
          </h1>

          <p className="hero-text">
            Find opportunities, connect with companies,
            and take the next step in your career.
          </p>

          <div className="hero-buttons">

            <button
              className="primary-button"
              onClick={() => setPage('login')}
            >
              Login
            </button>

            <button
              className="secondary-button"
              onClick={() => setPage('student-register')}
            >
              Register as Student
            </button>

          </div>

        </div>

      </main>

      <section className="features">

        <div className="feature-card">
          <h3>🎓 For Students</h3>

          <p>
            Create your profile and discover job
            opportunities from companies.
          </p>
        </div>

        <div className="feature-card">
          <h3>🏢 For Companies</h3>

          <p>
            Post job opportunities and connect
            with talented students.
          </p>
        </div>

        <div className="feature-card">
          <h3>💼 Find Opportunities</h3>

          <p>
            Search available jobs and find
            opportunities that match your skills.
          </p>
        </div>

      </section>

    </div>
  )
}

export default App