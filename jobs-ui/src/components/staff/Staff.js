import React, { Component } from 'react'
import { withOktaAuth } from '@okta/okta-react'
import { Link } from 'react-router-dom'
import M from 'materialize-css'
import API from '../misc/api'
import Pagination from '../misc/Pagination'
import Search from '../misc/Search'
import JobList from './JobList'

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
    },
    searchText: ''
  }

  pageDefaultNumber = 0
  pageDefaultSize = 10

  componentDidMount() {
    const floatingActionButton = document.querySelectorAll('.fixed-action-btn')
    M.FloatingActionButton.init(floatingActionButton, {
      direction: 'button'
    })

    this.getAllJobs(this.pageDefaultNumber, this.pageDefaultSize)
  }

  getAllJobs = async (page, size) => {
    API.get(`jobs?page=${page}&size=${size}`, {
      headers: {
        'Authorization': 'Bearer ' + await this.props.authState.accessToken.accessToken
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

  getJobsWithText = async (text, page, size) => {
    API.put(`jobs/search?page=${page}&size=${size}`, { 'text': text }, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + await this.props.authState.accessToken.accessToken
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

  deleteJob = async (id) => {
    API.delete(`jobs/${id}`, {
      headers: {
        'Authorization': 'Bearer ' + await this.props.authState.accessToken.accessToken
      }
    })
      .then(() => {
        const { number, size } = this.state.pagination
        this.getAllJobs(number, size)
      })
      .catch(error => {
        console.log(error)
        M.toast({ html: error, classes: 'rounded' })
      })
  }

  searchJob = async (searchText, pageNumber, pageSize) => {
    this.setState({ searchText })
    searchText ? this.getJobsWithText(searchText, pageNumber, pageSize) : this.getAllJobs(pageNumber, pageSize)
  }

  render() {
    return (
      <div>
        <div className="container">
          <Search searchJob={this.searchJob} />

          <Pagination className="center"
            pagination={this.state.pagination}
            searchText={this.state.searchText}
            searchJob={this.searchJob}
          />

          <JobList
            jobs={this.state.jobs}
            deleteJob={this.deleteJob}
          />
        </div>

        <div className="fixed-action-btn">
          <Link className="btn-floating btn-large waves-effect waves-light blue"
            to={'/staff/jobs'}>
            <i className="material-icons">add</i>
          </Link>
        </div>
      </div >
    )
  }
}

export default withOktaAuth(Staff)