import React from 'react'
import Logo from '../misc/Logo'
import TimesAgo from '../misc/TimesAgo'

function JobCard({ job }) {
  return (
    <div className="col s12">
      <div className="card-panel">
        <div className="row">
          <div className="col s3">
            <Logo logoUrl={job.logoUrl} />
          </div>
          <div className="col s9">
            <div className="row">
              <div className="col s12">
                <span className="right">{job.company}</span>
              </div>
            </div>
            <div className="row">
              <div className="col s12">
                <TimesAgo className="right" createDate={job.createDate} />
              </div>
            </div>
            <div className="row">
              <div className="col s12">
                <span className="right">{job.id}</span>
              </div>
            </div>
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
  )
}

export default JobCard