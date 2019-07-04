import React from 'react'
import Logo from '../misc/Logo'
import TimesAgo from '../misc/TimesAgo'

function JobCard({ job }) {
  return (
    <div className="col s12 m6 m6">
      <div className="card-panel">
        <div className="row">
          <div className="col s12">
            <div className="row">
              <div className="col s4">
                <Logo logoUrl={job.logoUrl} />
              </div>
              <div className="col s8">
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
              </div>
            </div>
            <div className="divider"></div>
            <div className="row">
              <div className="col s12">
                <span className="truncate">{job.title}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  )
}

export default JobCard