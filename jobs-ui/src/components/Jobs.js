import React from 'react'
import { Link } from 'react-router-dom'

function Jobs({ jobs }) {
  const jobList = jobs.map(job => {
    return (
      <Link className="black-text" to={'/jobs/' + job.id} component="Job" key={job.id}>
        <div className="col s12">
          <div className="card-panel">
            <div className="row">
              <div className="col s2 m2 l2">
                <img src={job.logoUrl} alt="" className="responsive-img" />
              </div>
              <div className="col s8 m8 l8">
                <span className="flow-text truncate">{job.title}</span>
              </div>
              <div className="col s2 m2 l2">
                <span className="right">{job.createDate}</span>
              </div>
            </div>
          </div>
        </div>
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