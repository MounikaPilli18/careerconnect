import { useState } from 'react'

function Login({ onLogin, onBack }) {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleLogin = async (event) => {
    event.preventDefault()

    setError('')
    setLoading(true)

    try {
      const credentials = btoa(`${username}:${password}`)

      const response = await fetch(
        'http://localhost:8081/companies/me',
        {
          method: 'GET',
          headers: {
            Authorization: `Basic ${credentials}`,
          },
        }
      )

      if (!response.ok) {
        throw new Error('Invalid username or password')
      }

      const profile = await response.json()

      onLogin({
        username,
        password,
        credentials,
        profile,
      })
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="login-page">
      <div className="login-card">
        <button className="back-btn" onClick={onBack}>
          ← Back
        </button>

        <h1>Welcome back</h1>

        <p className="login-subtitle">
          Login to your CareerConnect account
        </p>

        <form onSubmit={handleLogin}>
          <label>Username</label>

          <input
            type="text"
            value={username}
            onChange={(event) => setUsername(event.target.value)}
            placeholder="Enter username"
            required
          />

          <label>Password</label>

          <input
            type="password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            placeholder="Enter password"
            required
          />

          {error && (
            <p className="error-message">
              {error}
            </p>
          )}

          <button
            type="submit"
            className="login-submit"
            disabled={loading}
          >
            {loading ? 'Logging in...' : 'Login'}
          </button>
        </form>

        <p className="login-note">
          Student and company accounts use the same login.
        </p>
      </div>
    </div>
  )
}

export default Login