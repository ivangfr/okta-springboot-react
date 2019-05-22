import React from 'react'

function Jobs({ jobs, deleteJob, updateJob }) {
  const jobList = jobs.map(job => {
    return (
      <div className="collection-item grey lighten-5" key={job.id}>
        <div className="row">
          <div className="col s3 m2 l2">
            <img src={job.logoUrl} alt="" className="responsive-img" />
          </div>
          <div className="col s9 m10 l10">
            <span className="flow-text truncate">{job.title}</span>
          </div>
        </div>
        <div className="row">
          <div className="col s12">
            <span>Job ID: {job.id}</span>
            <button className="waves-effect waves-light btn-floating red darken-2 right" onClick={() => deleteJob(job.id)}>
              <i className="material-icons">delete</i>
            </button>
            <button data-target="modal" className="waves-effect waves-light btn-floating modal-trigger orange darken-2 right" onClick={() => updateJob(job)}>
              <i className="material-icons">edit</i>
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