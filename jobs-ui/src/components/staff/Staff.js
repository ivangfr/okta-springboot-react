import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import { withAuth } from '@okta/okta-react'
import M from 'materialize-css'
import JobList from './JobList'
import Search from '../misc/Search'
import API from '../misc/api'
import Pagination from '../misc/Pagination';

class Staff extends Component {
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

  componentDidMount() {
    const floatingActionButton = document.querySelectorAll('.fixed-action-btn')
    M.FloatingActionButton.init(floatingActionButton, {
      direction: 'button'
    })

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

  deleteJob = async (id) => {
    if (window.confirm(`Delete job with id ${id}`)) {
      API.delete(`jobs/${id}`, {
        headers: {
          'Authorization': 'Bearer ' + await this.props.auth.getAccessToken()
        }
      })
        .then(() => {
          const {number, size} = this.state.pagination
          this.getAllJobs(number, size)
        })
        .catch(error => {
          console.log(error)
          M.toast({ html: error, classes: 'rounded' })
        })
    }
  }

  searchJob = async (id) => {
    id ? this.getJobById(id) : this.getAllJobs(this.paginationDefaultNumber, this.paginationDefaultSize)
  }

  render() {
    return (
      <div>
        <div className="container">
          <Search searchJob={this.searchJob} />
          <Pagination pagination={this.state.pagination} getAllJobs={this.getAllJobs} className="center" />
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