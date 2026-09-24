import { useEffect, useState } from 'react'
import { apiRequest } from '../api/api'

function StudentProfile({ onBack, onLogout }) {
  const [form, setForm] = useState({
    name: '',
    phone: '',
    email: '',
    resume: '',
    location: '',
    cgpa: '',
  })

  const [username, setUsername] = useState('')
  const [saving, setSaving] = useState(false)
  const [loading, setLoading] = useState(true)
  const [message, setMessage] = useState('')
  const [error, setError] = useState('')

  useEffect(() => {
    apiRequest('/students/me')
      .then((data) => {
        setUsername(data.username || '')

        setForm({
          name: data.name || '',
          phone: data.phone || '',
          email: data.email || '',
          resume: data.resume || '',
          location: data.location || '',
          cgpa: data.cgpa ?? '',
        })
      })
      .catch((err) => {
        setError(err.message)
      })
      .finally(() => {
        setLoading(false)
      })
  }, [])

  const handleChange = (event) => {
    setForm({
      ...form,
      [event.target.name]: event.target.value,
    })
  }

  const handleSubmit = async (event) => {
    event.preventDefault()

    setSaving(true)
    setMessage('')
    setError('')

    try {
      await apiRequest('/students/me', {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          name: form.name,
          phone: form.phone,
          email: form.email,
          resume: form.resume,
          location: form.location,
          cgpa: form.cgpa === '' ? null : Number(form.cgpa),
        }),
      })

      setMessage('Profile updated successfully! 🎉')
    } catch (err) {
      setError(err.message)
    } finally {
      setSaving(false)
    }
  }

  if (loading) {
    return (
      <div className="page-message">
        Loading your profile...
      </div>
    )
  }

  return (
    <div className="form-page">

      <header className="form-header">
        <button
          className="back-button"
          onClick={onBack}
        >
          ← Back to Dashboard
        </button>

        <h1>My Profile</h1>

        <p>
          View and update your student profile.
        </p>
      </header>

      <main className="form-container">

        <div className="form-card">

          {message && (
            <div className="success-message">
              {message}
            </div>
          )}

          {error && (
            <div className="error-message">
              {error}
            </div>
          )}

          <div className="profile-field">
            <label>Username</label>

            <input
              type="text"
              value={username}
              disabled
            />
          </div>

          <div className="profile-field">
            <label>Name</label>

            <input
              type="text"
              name="name"
              value={form.name}
              onChange={handleChange}
              required
            />
          </div>

          <div className="profile-field">
            <label>Phone</label>

            <input
              type="tel"
              name="phone"
              value={form.phone}
              onChange={handleChange}
              required
            />
          </div>

          <div className="profile-field">
            <label>Email</label>

            <input
              type="email"
              name="email"
              value={form.email}
              onChange={handleChange}
              required
            />
          </div>

          <div className="profile-field">
            <label>Location</label>

            <input
              type="text"
              name="location"
              value={form.location}
              onChange={handleChange}
              placeholder="Bangalore"
            />
          </div>

          <div className="profile-field">
            <label>CGPA</label>

            <input
              type="number"
              name="cgpa"
              value={form.cgpa}
              onChange={handleChange}
              min="0"
              max="10"
              step="0.01"
              placeholder="8.5"
            />
          </div>

          <div className="profile-field">
            <label>Resume</label>

            <input
              type="text"
              name="resume"
              value={form.resume}
              onChange={handleChange}
              placeholder="resume.pdf"
            />
          </div>

          <button
            type="button"
            className="primary-button submit-button"
            onClick={handleSubmit}
            disabled={saving}
          >
            {saving ? 'Saving...' : 'Save Profile'}
          </button>

        </div>

      </main>

      <div
        style={{
          position: 'fixed',
          top: '20px',
          right: '20px',
        }}
      >
        <button
          className="logout-button"
          onClick={onLogout}
        >
          Logout
        </button>
      </div>

    </div>
  )
}

export default StudentProfile
