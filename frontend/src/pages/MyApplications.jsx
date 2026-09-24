import { useEffect, useState } from 'react'
import { apiRequest } from '../api/api'

function MyApplications({ onBack, onLogout }) {
  const [applications, setApplications] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    apiRequest('/applications/my')
      .then((data) => {
        setApplications(data)
      })
      .catch((err) => {
        setError(err.message)
      })
      .finally(() => {
        setLoading(false)
      })
  }, [])

  if (loading) {
    return (
      <div className="page-message">
        Loading your applications...
      </div>
    )
  }

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <div>
          <h1>CareerConnect</h1>
          <p>My Applications</p>
        </div>

        <button
          className="logout-button"
          onClick={onLogout}
        >
          Logout
        </button>
      </header>

      <main className="dashboard-content">
        <section className="jobs-section">
          <div className="section-heading">
            <div>
              <h2>My Applications</h2>

              <p>
                Track the jobs you have applied for.
              </p>
            </div>

            <button
              className="secondary-button"
              onClick={onBack}
            >
              ← Back to Jobs
            </button>
          </div>

          {error && (
            <div className="error-message">
              {error}
            </div>
          )}

          {!error && applications.length === 0 ? (
            <div className="empty-state">
              <div className="empty-icon">
                📄
              </div>

              <h3>No applications yet</h3>

              <p>
                Apply for a job to see it here.
              </p>

              <button
                className="primary-button"
                onClick={onBack}
              >
                Browse Jobs
              </button>
            </div>
          ) : (
            <div className="job-list">
              {applications.map((application) => (
                <div
                  className="job-card"
                  key={application.applicationId}
                >
                  <div>
                    <h3>
                      {application.jobTitle}
                    </h3>

                    <p>
                      {application.companyName} ·{' '}
                      {application.location}
                    </p>

                    <p>
                      Applied on:{' '}
                      {new Date(
                        application.appliedAt
                      ).toLocaleDateString()}
                    </p>
                  </div>

                  <div className="job-card-actions">
                    <div className="job-salary">
                      ₹{application.salary}
                    </div>

                    <div className="application-status">
                      {application.status}
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </section>
      </main>
    </div>
  )
}

export default MyApplications
