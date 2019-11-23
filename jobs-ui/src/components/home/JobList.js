import React from 'react'
import { Link } from 'react-router-dom'
import JobCard from './JobCard'

function Jobs({ jobs }) {
  const jobList = jobs.map(job => {
    return (
      <Link className="black-text" to={'/jobs/' + job.id} key={job.id}>
        <JobCard job={job} />
      </Link>
    )
  })
  
  return (
    <div className="row">
      {jobList}
    </div>
  )
}

export default Jobs