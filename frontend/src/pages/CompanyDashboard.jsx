import { useEffect, useState } from 'react'

function CompanyDashboard({ onPostJob, onLogout }) {
  const [profile, setProfile] = useState(null)
  const [jobs, setJobs] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    const username = localStorage.getItem('username')
    const password = localStorage.getItem('password')

    if (!username || !password) {
      setError('Login information is missing. Please login again.')
      setLoading(false)
      return
    }

    const credentials = btoa(`${username}:${password}`)

    Promise.all([
      fetch('http://localhost:8081/companies/me', {
        headers: {
          Authorization: `Basic ${credentials}`,
        },
      }),

      fetch('http://localhost:8081/jobs', {
        headers: {
          Authorization: `Basic ${credentials}`,
        },
      }),
    ])
      .then(async ([profileResponse, jobsResponse]) => {
        if (!profileResponse.ok) {
          throw new Error('Unable to load company profile')
        }

        const profileData = await profileResponse.json()

        let jobsData = []

        if (jobsResponse.ok) {
          const response = await jobsResponse.json()
          jobsData = response.data || []
        }

        
        setProfile(profileData)

        const companyJobs = jobsData.filter(
          (job) =>
            job.companyName?.toLowerCase() ===
            profileData.companyName?.toLowerCase()
        )
        
        setJobs(companyJobs)
      })
      .catch((err) => {
        setError(err.message)
      })
      .finally(() => {
        setLoading(false)
      })
  }, [])

  if (loading) {
    return <div className="page-message">Loading dashboard...</div>
  }

  if (error) {
    return (
      <div className="page-message error">
        {error}
      </div>
    )
  }

  return (
    <div className="dashboard">
      <header className="dashboard-header">
        <div>
          <h1>CareerConnect</h1>
          <p>Company Dashboard</p>
        </div>

        <button
          className="logout-button"
          onClick={onLogout}
        >
          Logout
        </button>
      </header>

      <main className="dashboard-content">
        <section className="welcome-card">
          <div>
            <p className="small-label">WELCOME BACK</p>

            <h2>
              Welcome, {profile?.companyName || profile?.username}! 👋
            </h2>

            <p>
              You are logged in as{' '}
              <strong>{profile?.role || 'COMPANY'}</strong>.
            </p>
          </div>

          <button
            className="primary-button"
            onClick={onPostJob}
          >
            + Post a New Job
          </button>
        </section>

        <section className="stats">
          <div className="stat-card">
            <span>💼</span>
            <div>
              <h3>{jobs.length}</h3>
              <p>Jobs Posted</p>
            </div>
          </div>

          <div className="stat-card">
            <span>🏢</span>
            <div>
              <h3>{profile?.companyName || '-'}</h3>
              <p>Company</p>
            </div>
          </div>

          <div className="stat-card">
            <span>📍</span>
            <div>
              <h3>{profile?.companyLocation || '-'}</h3>
              <p>Location</p>
            </div>
          </div>
        </section>

        <section className="jobs-section">
          <div className="section-heading">
            <div>
              <h2>Your Job Opportunities</h2>
              <p>Manage the jobs posted by your company.</p>
            </div>

            <button
              className="secondary-button"
              onClick={onPostJob}
            >
              + Post Job
            </button>
          </div>

          {jobs.length === 0 ? (
            <div className="empty-state">
              <div className="empty-icon">💼</div>

              <h3>No jobs posted yet</h3>

              <p>
                Create your first job opportunity and start
                finding talented candidates.
              </p>

              <button
                className="primary-button"
                onClick={onPostJob}
              >
                Post Your First Job
              </button>
            </div>
          ) : (
            <div className="job-list">
              {jobs.map((job) => (
                <div
                  className="job-card"
                  key={job.jobId}
                >
                  <div>
                    <h3>{job.jobTitle}</h3>

                    <p>
                      {job.companyName} · {job.location}
                    </p>
                  </div>

                  <div className="job-salary">
                    ₹{job.salary}
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

export default CompanyDashboard