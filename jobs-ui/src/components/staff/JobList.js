import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import M from 'materialize-css'
import Logo from '../misc/Logo'
import DeleteDialog from './DeleteDialog'

class Jobs extends Component {
  state = {
    job: null
  }

  deleteModal = null

  componentDidMount() {
    this.deleteModal = M.Modal.init(document.getElementById('deleteModal'))
  }

  showDialog = (job) => {
    this.setState({ job })
    this.deleteModal.open()
  }

  render() {
    const jobList = this.props.jobs.map(job => {
      return (
        <div className="collection-item" key={job.id}>
          <div className="row">
            <Link className="black-text" to={'/staff/jobs/' + job.id}>
              <div className="col s11">
                <div className="row">
                  <div className="col s3 m2 l2">
                    <Logo logoUrl={job.logoUrl} />
                  </div>
                  <div className="col s9 m10 l10">
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
              <button className="waves-effect waves-light btn btn-floating red darken-2" onClick={() => this.showDialog(job)}>
                <i className="material-icons">delete</i>
              </button>
            </div>
          </div>
        </div>
      )
    })

    return (
      <div>
        <div className="collection">
          {jobList}
        </div>

        <DeleteDialog
          job={this.state.job}
          deleteJob={this.props.deleteJob}
        />
      </div >
    )
  }
}

export default Jobs