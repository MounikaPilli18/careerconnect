import { useState } from 'react'

function StudentRegister({ onBack, onRegistered }) {
  const [form, setForm] = useState({
    username: '',
    password: '',
    name: '',
    phone: '',
    email: '',
    resume: '',
    location: '',
    cgpa: '',
  })

  const [error, setError] = useState('')
  const [loading, setLoading] = useState(false)

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    })
  }

  const handleSubmit = async (e) => {
    e.preventDefault()
    setError('')
    setLoading(true)

    try {
      const response = await fetch(
        'http://localhost:8081/students/register',
        {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify({
            ...form,
            cgpa: Number(form.cgpa),
          }),
        }
      )

      if (!response.ok) {
        throw new Error(
          'Registration failed. Please check your details.'
        )
      }

      const data = await response.json()

      onRegistered(data)
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="auth-page">
      <div className="auth-card">

        <button
          className="back-button"
          onClick={onBack}
        >
          ← Back
        </button>

        <h1>Create Student Account</h1>

        <p>
          Join CareerConnect and find your dream job.
        </p>

        {error && (
          <div className="error-message">
            {error}
          </div>
        )}

        <form onSubmit={handleSubmit}>

          <label>Username</label>
          <input
            type="text"
            name="username"
            value={form.username}
            onChange={handleChange}
            required
          />

          <label>Password</label>
          <input
            type="password"
            name="password"
            value={form.password}
            onChange={handleChange}
            required
          />

          <label>Full Name</label>
          <input
            type="text"
            name="name"
            value={form.name}
            onChange={handleChange}
            required
          />

          <label>Phone</label>
          <input
            type="text"
            name="phone"
            value={form.phone}
            onChange={handleChange}
            required
          />

          <label>Email</label>
          <input
            type="email"
            name="email"
            value={form.email}
            onChange={handleChange}
            required
          />

          <label>Resume</label>
          <input
            type="text"
            name="resume"
            value={form.resume}
            onChange={handleChange}
            placeholder="Resume link"
          />

          <label>Location</label>
          <input
            type="text"
            name="location"
            value={form.location}
            onChange={handleChange}
            required
          />

          <label>CGPA</label>
          <input
            type="number"
            name="cgpa"
            value={form.cgpa}
            onChange={handleChange}
            step="0.01"
            min="0"
            max="10"
            required
          />

          <button
            type="submit"
            className="primary-button"
            disabled={loading}
          >
            {loading ? 'Creating Account...' : 'Register'}
          </button>

        </form>
      </div>
    </div>
  )
}

export default StudentRegister