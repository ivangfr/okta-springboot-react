import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import { withAuth } from '@okta/okta-react'
import M from 'materialize-css'
import axios from 'axios'
import JobList from './JobList'
import Search from '../misc/Search'

class Staff extends Component {
  state = {
    jobs: []
  }

  componentDidMount() {
    const floatingActionButton = document.querySelectorAll('.fixed-action-btn')
    M.FloatingActionButton.init(floatingActionButton, {
      direction: 'button'
    })

    this.refresh()
  }

  refresh = async () => {
    axios.get('http://localhost:8080/api/jobs', {
      headers: {
        'Authorization': 'Bearer ' + await this.props.auth.getAccessToken()
      }
    })
      .then(response => {
        this.setState({
          jobs: response.data.content
        })
      })
      .catch(error => {
        console.log(error)
        M.toast({html: error, classes: 'rounded'})
      })
  }

  deleteJob = async (id) => {
    axios.delete('http://localhost:8080/api/jobs/' + id, {
      headers: {
        'Authorization': 'Bearer ' + await this.props.auth.getAccessToken()
      }
    })
      .then(() => this.refresh())
      .catch(error => {
        console.log(error)
        M.toast({html: error, classes: 'rounded'})
      })
  }

  render() {
    return (
      <div>
        <div className="container">
          <Search />
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