import React from 'react'
import { Link } from 'react-router-dom'

function Jobs({ jobs }) {
  const jobList = jobs.map(job => {
    return (
      <Link className="black-text" to={'/jobs/' + job.id} component="Job" key={job.id}>
        <div className="col s12">
          <div className="card-panel">
            <div className="row">
              <div className="col s3">
                <img src={job.logoUrl} alt="" className="responsive-img" />
              </div>
              <div className="col s3 offset-s6">
                <ul className="blue-text text-darken-2" style={{ "margin": "0px" }}>
                  <li><span>{job.id}</span></li>
                  <li><span>{job.createDate}</span></li>
                </ul>
              </div>
            </div>
            <div className="divider"></div>
            <div className="row">
              <div className="col s12">
                <span className="flow-text truncate" style={{ "marginTop": "5px" }}>{job.title}</span>
              </div>
            </div>
            <div className="row">
              <div className="col s12">
                <span className="truncate">{job.description}</span>
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