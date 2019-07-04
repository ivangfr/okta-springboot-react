import React from 'react'

function DeleteDialog({ job, deleteJob }) {
  const jobId = job && job.id
  const jobCompany = job && job.company
  
  return (
    <div id="deleteModal" className="modal">
      <div className="modal-content">
        <h4>Delete Job?</h4>
        <p>Do you confirm the deletion of the <span className="blue-text text-darken-3">{jobCompany}</span> job, id <span className="blue-text text-darken-3">{jobId}</span>?</p>
      </div>
      <div className="modal-footer">
        <button className="modal-close waves-effect waves-green btn-flat">No</button>
        <button className="modal-close waves-effect waves-green btn-flat" onClick={() => deleteJob(job.id)}>Yes</button>
      </div>
    </div>
  )
}

export default DeleteDialog