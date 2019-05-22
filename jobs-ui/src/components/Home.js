import React, { Component } from 'react'
import Jobs from './Jobs'

class Home extends Component {
  state = {
    jobs: []
  }

  componentDidMount() {
    fetch("http://localhost:8080/api/jobs/last6")
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
        <h5>Last 6 jobs added</h5>
        <Jobs jobs={this.state.jobs} />
      </div>
    )
  }
}

export default Home