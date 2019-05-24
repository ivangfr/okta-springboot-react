import React from 'react'
import { Link } from 'react-router-dom'
import Logo from '../Logo'

function Jobs({ jobs, deleteJob }) {
  const jobList = jobs.map(job => {
    return (
      <div className="collection-item" key={job.id}>
        <div className="row">
          <div className="col s3 m2 l2">
            <Logo logoUrl={job.logoUrl} />
          </div>
          <div className="col s6 m8 l8">
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
          <div className="col s2 m2 l2">
            <Link className="waves-effect waves-light orange darken-2 modal-trigger btn-floating btn-small right" to={'/staff/jobs/' + job.id} component="JobForm">
              <i className="material-icons">edit</i>
            </Link>
            <button className="waves-effect waves-light red darken-2 btn-floating btn-small right" onClick={() => deleteJob(job.id)}>
              <i className="material-icons">delete</i>
            </button>
          </div>
        </div>
        <div className="row">
          <div className="col s12">
            <span className="flow-text truncate">{job.title}</span>
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