import React, { Component } from 'react'
import M from 'materialize-css'
import JobList from './JobList'
import API from '../misc/api'

class Home extends Component {
  state = {
    jobs: []
  }

  componentDidMount() {
    API.get(`jobs/last6`)
      .then(response => {
        this.setState({
          jobs: response.data
        })
      })
      .catch(error => {
        console.log(error)
        M.toast({ html: error, classes: 'rounded' })
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