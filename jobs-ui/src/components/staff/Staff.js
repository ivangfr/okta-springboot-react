import React, { Component } from 'react'
import Jobs from './Jobs'
import Search from '../Search'
import JobModalForm from './JobModalForm'
import M from 'materialize-css';

class Staff extends Component {
  state = {
    jobs: [],
    updatingJob: null
  }

  componentDidMount() {
    const floatingActionButton = document.querySelectorAll('.fixed-action-btn');
    M.FloatingActionButton.init(floatingActionButton, {
      direction: 'button'
    });

    this.refresh()
  }

  refresh = (e) => {
    fetch("http://localhost:8080/api/jobs")
      .then(res => res.json())
      .then(jobs => {
        this.setState({
          jobs,
          updatingJob: null
        })
      })
  }

  deleteJob = (id) => {
    fetch('http://localhost:8080/api/jobs/' + id, {
      method: "DELETE"
    })
      .then(() => this.refresh())
  }

  saveJob = (job) => {
    const id = job.id

    let httpMethod = 'POST'
    let url = 'http://localhost:8080/api/jobs'
    if (id) {
      httpMethod = 'PUT'
      url += '/' + id
    }

    fetch(url, {
      method: httpMethod,
      body: JSON.stringify(job),
      headers: {
        'Content-Type': 'application/json'
      },
    })
      .then(() => {
        this.refresh()
      })
  }

  updateJob = (job) => {
    this.setState({
      updatingJob: job
    })
  }

  render() {
    return (
      <div>
        <div className="container">
          <Search />
          <Jobs
            jobs={this.state.jobs}
            deleteJob={this.deleteJob}
            updateJob={this.updateJob}
          />
        </div>

        <div className="fixed-action-btn">
          <button data-target="modal" className="btn-floating modal-trigger btn-large waves-effect waves-light blue">
            <i className="material-icons">add</i>
          </button>
        </div>

        <JobModalForm updatingJob={this.state.updatingJob} saveJob={this.saveJob} />
      </div >
    )
  }
}

export default Staff