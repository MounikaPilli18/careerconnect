import { useEffect, useState } from 'react'
import { apiRequest } from '../api/api'

function CompanyDashboard({ onPostJob, onLogout }) {
  const [profile, setProfile] = useState(null)
  const [jobs, setJobs] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [applicants, setApplicants] = useState({})
  const [selectedJobId, setSelectedJobId] = useState(null)

  useEffect(() => {
    Promise.all([
      apiRequest('/companies/me'),
      apiRequest('/jobs'),
    ])
      .then(([profileData, jobsData]) => {
        setProfile(profileData)

        const allJobs = jobsData.data || []

        const companyJobs = allJobs.filter(
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

  const handleViewApplicants = async (jobId) => {
    if (selectedJobId === jobId) {
      setSelectedJobId(null)
      return
    }

    try {
      const data = await apiRequest(
        `/applications/job/${jobId}`
      )

      setApplicants((previousApplicants) => ({
        ...previousApplicants,
        [jobId]: data,
      }))

      setSelectedJobId(jobId)
      setError('')
    } catch (err) {
      setError(err.message)
    }
  }

  if (loading) {
    return (
      <div className="page-message">
        Loading dashboard...
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
            <p className="small-label">
              WELCOME BACK
            </p>

            <h2>
              Welcome, {profile?.companyName || profile?.username}! 👋
            </h2>

            <p>
              You are logged in as{' '}
              <strong>{profile?.role || 'COMPANY'}</strong>.
            </p>
          </div>

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

              <p>
                Manage the jobs posted by your company.
              </p>
            </div>

            <button
  className="secondary-button post-job-button"
  onClick={onPostJob}
>
  Post a Job
</button>
          </div>

          {error && (
            <div className="error-message">
              {error}
            </div>
          )}

          {jobs.length === 0 ? (
            <div className="empty-state">
              <div className="empty-icon">
                💼
              </div>

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
              {jobs.map((job) => {
                const jobApplicants =
                  applicants[job.jobId] || []

                const isSelected =
                  selectedJobId === job.jobId

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
                        <p>
                          {job.description}
                        </p>
                      )}
                    </div>

                    <div className="job-card-actions">
                      <div className="job-salary">
                        ₹{job.salary}
                      </div>

                      <button
                        className="secondary-button"
                        onClick={() =>
                          handleViewApplicants(job.jobId)
                        }
                      >
                        {isSelected
                          ? 'Hide Applicants'
                          : 'View Applicants'}
                      </button>
                    </div>

                    {isSelected && (
                      <div className="applicants-section">
                        <h4>
                          Applicants ({jobApplicants.length})
                        </h4>

                        {jobApplicants.length === 0 ? (
                          <p>
                            No students have applied for
                            this job yet.
                          </p>
                        ) : (
                          <div className="applicant-list">
                            {jobApplicants.map(
                              (applicant) => (
                                <div
                                  className="applicant-card"
                                  key={
                                    applicant.applicationId
                                  }
                                >
                                  <h4>
                                    {applicant.studentName}
                                  </h4>

                                  <p>
                                    <strong>Email:</strong>{' '}
                                    {applicant.email}
                                  </p>

                                  <p>
                                    <strong>Phone:</strong>{' '}
                                    {applicant.phone}
                                  </p>

                                  <p>
                                    <strong>Location:</strong>{' '}
                                    {applicant.location}
                                  </p>

                                  <p>
                                    <strong>CGPA:</strong>{' '}
                                    {applicant.cgpa}
                                  </p>

                                  <p>
                                    <strong>Resume:</strong>{' '}
                                    {applicant.resume ||
                                      'Not provided'}
                                  </p>

                                  <p>
                                    <strong>Status:</strong>{' '}
                                    {applicant.status}
                                  </p>
                                </div>
                              )
                            )}
                          </div>
                        )}
                      </div>
                    )}
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

export default CompanyDashboard