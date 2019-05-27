import React, { Component } from 'react'
import axios from 'axios'
import JobList from './JobList'
import M from 'materialize-css'

class Home extends Component {
  state = {
    jobs: []
  }

  componentDidMount() {
    axios.get('http://localhost:8080/api/jobs/last6')
      .then(response => {
        this.setState({
          jobs: response.data
        })
      })
      .catch(error => {
        console.log(error)
        M.toast({html: error, classes: 'rounded'})
      })
  }

  render() {
    return (
      <div className="container">
        <h5>Last 6 jobs added</h5>
        <JobList jobs={this.state.jobs} />
      </div>
    )
  }
}

export default Home