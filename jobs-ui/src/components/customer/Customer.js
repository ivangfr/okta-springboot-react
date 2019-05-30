import React, { Component } from 'react'
import { withAuth } from '@okta/okta-react'
import M from 'materialize-css'
import JobList from './JobList'
import Search from '../misc/Search'
import API from '../misc/api'
import Pagination from '../misc/Pagination';

class Customer extends Component {
  state = {
    jobs: [],
    pagination: {
      first: null,
      last: null,
      number: null,
      size: null,
      totalElements: null,
      totalPages: null
    }
  }

  paginationDefaultNumber = 0;
  paginationDefaultSize = 10;

  async componentDidMount() {
    this.getAllJobs(this.paginationDefaultNumber, this.paginationDefaultSize)
  }

  getAllJobs = async (page, size) => {
    API.get(`jobs?page=${page}&size=${size}`, {
      headers: {
        'Authorization': 'Bearer ' + await this.props.auth.getAccessToken()
      }
    })
      .then(response => {
        const { content, first, last, number, size, totalElements, totalPages } = response.data
        this.setState({
          jobs: content,
          pagination: {
            first,
            last,
            number,
            size,
            totalElements,
            totalPages
          }
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
    id ? this.getJobById(id) : this.getAllJobs(this.paginationDefaultNumber, this.paginationDefaultSize)
  }

  render() {
    return (
      <div className="container">
        <Search searchJob={this.searchJob} />
        <Pagination pagination={this.state.pagination} getAllJobs={this.getAllJobs} className="center" />
        <JobList jobs={this.state.jobs} />
      </div>
    )
  }
}

export default withAuth(Customer)