import React, { Component } from 'react'
import { withOktaAuth } from '@okta/okta-react'
import M from 'materialize-css'
import JobList from './JobList'
import Search from '../misc/Search'
import API from '../misc/api'
import Pagination from '../misc/Pagination'

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
    },
    searchText: ''
  }

  pageDefaultNumber = 0
  pageDefaultSize = 10

  async componentDidMount() {
    this.getAllJobs(this.pageDefaultNumber, this.pageDefaultSize)
  }

  getAllJobs = async (page, size) => {
    API.get(`jobs?page=${page}&size=${size}`, {
      headers: {
        'Authorization': 'Bearer ' + await this.props.authState.accessToken.value
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
        'Authorization': 'Bearer ' + await this.props.authState.accessToken.value
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

  searchJob = async (searchText, pageNumber, pageSize) => {
    this.setState({ searchText })
    searchText ? this.getJobsWithText(searchText, pageNumber, pageSize) : this.getAllJobs(pageNumber, pageSize)
  }

  render() {
    const { jobs, pagination, searchText } = this.state
    
    return (
      <div className="container">
        <Search searchJob={this.searchJob} />

        <Pagination className="center"
          pagination={pagination}
          searchText={searchText}
          searchJob={this.searchJob}
        />

        <JobList jobs={jobs} />
      </div>
    )
  }
}

export default withOktaAuth(Customer)