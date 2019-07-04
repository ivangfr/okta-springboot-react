import React, { Component } from 'react'
import M from 'materialize-css'
import JobList from './JobList'
import API from '../misc/api'

class Home extends Component {
  state = {
    jobs: []
  }

  componentDidMount() {
    API.get(`jobs/newest?number=8`)
      .then(response => {
        this.setState({
          jobs: response.data
        })
      })
      .catch(error => {
        console.log(error)
        M.toast({ html: error, classes: 'rounded' })
      })

    M.Parallax.init(document.querySelectorAll('.parallax'))
  }

  render() {
    const { jobs } = this.state
    
    return (
      <div>
        <div className="parallax-container">
          <div className="parallax"><img src="/nyc.jpg" alt="" /></div>
        </div>
        <div className="section white">
          <div className="container">
            <JobList jobs={jobs} />
          </div>
        </div>
      </div>
    )
  }
}

export default Home