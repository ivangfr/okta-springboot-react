import React from 'react'
import { Link } from 'react-router-dom'
import Logo from '../misc/Logo'

function Jobs({ jobs, deleteJob }) {
  const jobList = jobs.map(job => {
    return (
      <div className="collection-item" key={job.id}>
        <div className="row">
          <Link className="black-text" to={'/staff/jobs/' + job.id} component="JobForm">
            <div className="col s11">
              <div className="row">
                <div className="col s3 m2 l2">
                  <Logo logoUrl={job.logoUrl} />
                </div>
                <div className="col s12 m10 l10">
                  <div className="row">
                    <div className="col s12">
                      <span className="right">{job.company}</span>
                    </div>
                  </div>
                  <div className="row">
                    <div className="col s12">
                      <span className="right">{job.id}</span>
                    </div>
                  </div>
                </div>
              </div>
              <div className="row">
                <div className="col s12">
                  <span className="flow-text truncate">{job.title}</span>
                </div>
              </div>
            </div>
          </Link>
          <div className="col s1">
            <button className="waves-effect waves-light btn btn-floating red darken-2" onClick={() => deleteJob(job.id)}>
              <i className="material-icons">delete</i>
            </button>
          </div>
        </div>
      </div>
    )
  })
  return (
    <div className="collection">
      {jobList}
    </div>
  )
}

export default Jobs