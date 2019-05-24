import React, { Component } from 'react'
import axios from 'axios'
import JobList from './JobList'
import Search from '../misc/Search'

class Customer extends Component {
  state = {
    jobs: []
  }

  componentDidMount() {
    axios.get('http://localhost:8080/api/jobs')
      .then(response => {
        this.setState({
          jobs: response.data.content
        })
      })
      .catch(error => console.log(error))
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