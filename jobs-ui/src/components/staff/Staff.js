import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import { withAuth } from '@okta/okta-react'
import M from 'materialize-css'
import JobList from './JobList'
import Search from '../misc/Search'
import API from '../misc/api'

class Staff extends Component {
  state = {
    jobs: []
  }

  componentDidMount() {
    const floatingActionButton = document.querySelectorAll('.fixed-action-btn')
    M.FloatingActionButton.init(floatingActionButton, {
      direction: 'button'
    })

    this.getAllJobs(0, 10)
  }

  getAllJobs = async (page, size) => {
    API.get(`jobs?page=${page}&size=${size}`, {
      headers: {
        'Authorization': 'Bearer ' + await this.props.auth.getAccessToken()
      }
    })
      .then(response => {
        this.setState({
          jobs: response.data.content
        })
      })
      .catch(error => {
        console.log(error)
        M.toast({ html: error, classes: 'rounded' })
      })
  }

  getJobById = async (id) => {
    API.get(`jobs/${id}`, {
      headers: {
        'Authorization': 'Bearer ' + await this.props.auth.getAccessToken()
      }
    })
      .then(response => {
        this.setState({
          jobs: [response.data]
        })
      })
      .catch(error => {
        console.log(error)
        M.toast({ html: error, classes: 'rounded' })
      })
  }

  deleteJob = async (id) => {
    API.delete(`jobs/${id}`, {
      headers: {
        'Authorization': 'Bearer ' + await this.props.auth.getAccessToken()
      }
    })
      .then(() => this.getAllJobs(0, 10))
      .catch(error => {
        console.log(error)
        M.toast({ html: error, classes: 'rounded' })
      })
  }

  searchJob = async (id) => {
    id ? this.getJobById(id) : this.getAllJobs(0, 10)
  }

  render() {
    return (
      <div>
        <div className="container">
          <Search searchJob={this.searchJob} />
          <JobList
            jobs={this.state.jobs}
            deleteJob={this.deleteJob}
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

export default withAuth(Staff)