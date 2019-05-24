import React, { Component } from 'react'
import JobList from './JobList'
import Search from '../misc/Search'

class Customer extends Component {
  state = {
    jobs: []
  }

  componentDidMount() {
    fetch("http://localhost:8080/api/jobs")
      .then(res => res.json())
      .then(jobs => {
        this.setState({
          jobs
        })
      })
  }

  render() {
    return (
      <div className="container">
        <Search />
        <JobList jobs={this.state.jobs} />
      </div>
    )
  }
}

export default Customer