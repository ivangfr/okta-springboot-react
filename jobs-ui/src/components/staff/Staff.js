import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import M from 'materialize-css';
import axios from 'axios';
import JobList from './JobList'
import Search from '../misc/Search'

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
    axios.get('http://localhost:8080/api/jobs')
      .then(response => {
        this.setState({
          jobs: response.data,
          updatingJob: null
        })
      })
      .catch(error => console.log(error))
  }

  deleteJob = (id) => {
    axios.delete('http://localhost:8080/api/jobs/' + id)
      .then(() => this.refresh())
      .catch(error => console.log(error))
  }

  saveJob = (job) => {
    const id = job.id

    let method = 'POST'
    let url = 'http://localhost:8080/api/jobs'
    if (id) {
      method = 'PUT'
      url += '/' + id
    }

    axios({
      method: method,
      url: url,
      headers: {
        'Content-Type': 'application/json'
      },
      data: JSON.stringify(job)
    }).then(() => {
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
          <JobList
            jobs={this.state.jobs}
            deleteJob={this.deleteJob}
            updateJob={this.updateJob}
          />
        </div>

        <div className="fixed-action-btn">
          <Link className="btn-floating modal-trigger btn-large waves-effect waves-light blue" to={'/staff/jobs'} component="JobForm">
            <i className="material-icons">add</i>
          </Link>
        </div>
      </div >
    )
  }
}

export default Staff