import { useState } from 'react'

function PostJob({ onBack }) {
  const [form, setForm] = useState({
    jobTitle: '',
    companyName: '',
    location: '',
    salary: '',
    description: '',
  })

  const [message, setMessage] = useState('')
  const [error, setError] = useState('')
  const [saving, setSaving] = useState(false)

  const handleChange = (event) => {
    setForm({
      ...form,
      [event.target.name]: event.target.value,
    })
  }

  const handleSubmit = async (event) => {
    event.preventDefault()

    setMessage('')
    setError('')
    setSaving(true)

    const username = localStorage.getItem('username')
    const password = localStorage.getItem('password')

    if (!username || !password) {
      setError('Login information is missing.')
      setSaving(false)
      return
    }

    const credentials = btoa(`${username}:${password}`)

    try {
      const response = await fetch(
        'http://localhost:8081/jobs',
        {
          method: 'POST',

          headers: {
            'Content-Type': 'application/json',
            Authorization: `Basic ${credentials}`,
          },

          body: JSON.stringify({
            jobTitle: form.jobTitle,
            companyName: form.companyName,
            location: form.location,
            salary: Number(form.salary),
            description: form.description,
          }),
        }
      )

      const data = await response.json()

      if (!response.ok) {
        throw new Error(
          data.message || 'Failed to create job'
        )
      }

      setMessage('Job posted successfully! 🎉')

      setForm({
        jobTitle: '',
        companyName: '',
        location: '',
        salary: '',
        description: '',
      })
    } catch (err) {
      setError(err.message)
    } finally {
      setSaving(false)
    }
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

        <h1>Post a New Job</h1>

        <p>
          Find talented candidates for your company.
        </p>
      </header>

      <main className="form-container">
        <form
          onSubmit={handleSubmit}
          className="job-form"
        >
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

          <label>
            Job Title

            <input
              type="text"
              name="jobTitle"
              value={form.jobTitle}
              onChange={handleChange}
              placeholder="Java Spring Boot Developer"
              required
            />
          </label>

          <label>
            Company Name

            <input
              type="text"
              name="companyName"
              value={form.companyName}
              onChange={handleChange}
              placeholder="Wipro"
              required
            />
          </label>

          <label>
            Location

            <input
              type="text"
              name="location"
              value={form.location}
              onChange={handleChange}
              placeholder="Bangalore"
              required
            />
          </label>

          <label>
            Salary

            <input
              type="number"
              name="salary"
              value={form.salary}
              onChange={handleChange}
              placeholder="600000"
              min="0"
              required
            />
          </label>

          <label>
            Job Description

            <textarea
              name="description"
              value={form.description}
              onChange={handleChange}
              placeholder="Describe the job..."
              rows="6"
              required
            />
          </label>

          <button
            type="submit"
            className="primary-button submit-button"
            disabled={saving}
          >
            {saving ? 'Posting...' : 'Post Job'}
          </button>
        </form>
      </main>
    </div>
  )
}

export default PostJob