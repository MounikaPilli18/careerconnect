import { useEffect, useState } from 'react'
import { apiRequest } from '../api/api'

function StudentDashboard({ onLogout, onMyApplications, onMyProfile }) {
  const [profile, setProfile] = useState(null)
  const [jobs, setJobs] = useState([])
  const [appliedJobIds, setAppliedJobIds] = useState([])
  const [applyingJobId, setApplyingJobId] = useState(null)
  const [search, setSearch] = useState('')
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')

  useEffect(() => {
    Promise.all([
      apiRequest('/students/me'),
      apiRequest('/jobs'),
      apiRequest('/applications/my'),
    ])
      .then(([profileData, jobsData, applicationsData]) => {
        setProfile(profileData)
        setJobs(jobsData.data || [])

        const appliedIds = applicationsData.map(
          (application) => application.jobId
        )

        setAppliedJobIds(appliedIds)
      })
      .catch((err) => {
        setError(err.message)
      })
      .finally(() => {
        setLoading(false)
      })
  }, [])

  const handleApply = async (jobId) => {
    setApplyingJobId(jobId)
    setError('')

    try {
      await apiRequest(`/applications/apply/${jobId}`, {
        method: 'POST',
      })

      setAppliedJobIds((previousIds) => [
        ...previousIds,
        jobId,
      ])
    } catch (err) {
      setError(err.message)
    } finally {
      setApplyingJobId(null)
    }
  }

  const filteredJobs = jobs.filter((job) => {
    const searchText = search.toLowerCase().trim()

    if (!searchText) {
      return true
    }

    return (
      job.jobTitle?.toLowerCase().includes(searchText) ||
      job.companyName?.toLowerCase().includes(searchText) ||
      job.location?.toLowerCase().includes(searchText) ||
      job.description?.toLowerCase().includes(searchText)
    )
  })

  if (loading) {
    return (
      <div className="page-message">
        Loading student dashboard...
      </div>
    )
  }

  if (error && !profile) {
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
          <p>Student Dashboard</p>
        </div>

<div className="dashboard-header-actions">
  <button
    className="secondary-button"
    onClick={onMyApplications}
  >
    My Applications
  </button>

<button
  className="secondary-button"
  onClick={onMyProfile}
>
  My Profile
</button>


  <button
    className="logout-button"
    onClick={onLogout}
  >
    Logout
  </button>
</div>


      </header>

      <main className="dashboard-content">
        <section className="welcome-card">
          <div>
            <p className="small-label">
              WELCOME BACK
            </p>

            <h2>
              Welcome, {profile?.name || profile?.username}! 👋
            </h2>

            <p>
              Find opportunities that match your career goals.
            </p>
          </div>
        </section>

        <section className="stats">
          <div className="stat-card">
            <span>💼</span>

            <div>
              <h3>{jobs.length}</h3>
              <p>Available Jobs</p>
            </div>
          </div>

          <div className="stat-card">
            <span>🎓</span>

            <div>
              <h3>{profile?.cgpa ?? '-'}</h3>
              <p>CGPA</p>
            </div>
          </div>

          <div className="stat-card">
            <span>📍</span>

            <div>
              <h3>{profile?.location || '-'}</h3>
              <p>Location</p>
            </div>
          </div>
        </section>

        <section className="jobs-section">
          <div className="section-heading">
            <div>
              <h2>Available Jobs</h2>

              <p>
                Explore jobs posted by companies.
              </p>
            </div>
          </div>

          <div className="search-box">
            <input
              type="text"
              value={search}
              onChange={(event) => setSearch(event.target.value)}
              placeholder="Search jobs, companies, locations..."
            />
          </div>

          {error && (
            <div className="error-message">
              {error}
            </div>
          )}

          {filteredJobs.length === 0 ? (
            <div className="empty-state">
              <h3>No jobs found</h3>

              <p>
                Try another search term.
              </p>
            </div>
          ) : (
            <div className="job-list">
              {filteredJobs.map((job) => {
                const hasApplied = appliedJobIds.includes(job.jobId)
                const isApplying = applyingJobId === job.jobId

                return (
                  <div
                    className="job-card"
                    key={job.jobId}
                  >
                    <div>
                      <h3>{job.jobTitle}</h3>

                      <p>
                        {job.companyName} · {job.location}
                      </p>

                      {job.description && (
                        <p>{job.description}</p>
                      )}
                    </div>

                    <div className="job-card-actions">
                      <div className="job-salary">
                        ₹{job.salary}
                      </div>

                      <button
                        className={
                          hasApplied
                            ? 'applied-button'
                            : 'primary-button'
                        }
                        onClick={() => handleApply(job.jobId)}
                        disabled={hasApplied || isApplying}
                      >
                        {hasApplied
                          ? '✓ Applied'
                          : isApplying
                            ? 'Applying...'
                            : 'Apply Now'}
                      </button>
                    </div>
                  </div>
                )
              })}
            </div>
          )}
        </section>
      </main>
    </div>
  )
}

export default StudentDashboard