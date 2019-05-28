import React, { Component } from 'react'
import { withAuth } from '@okta/okta-react'
import M from 'materialize-css'
import JobList from './JobList'
import Search from '../misc/Search'
import API from '../misc/api'
import Pagination from '../misc/Pagination';

class Customer extends Component {
  state = {
    jobs: []
  }

  async componentDidMount() {
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

  searchJob = async (id) => {
    id ? this.getJobById(id) : this.getAllJobs(0, 10)
  }

  render() {
    return (
      <div className="container">
        <Search searchJob={this.searchJob} />
        <Pagination className="center" />
        <JobList jobs={this.state.jobs} />
      </div>
    )
  }
}

export default withAuth(Customer)