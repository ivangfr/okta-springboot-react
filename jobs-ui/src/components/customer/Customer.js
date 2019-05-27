import React, { Component } from 'react'
import { withAuth } from '@okta/okta-react'
import axios from 'axios'
import M from 'materialize-css'
import JobList from './JobList'
import Search from '../misc/Search'

class Customer extends Component {
  state = {
    jobs: []
  }

  async componentDidMount() {
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

  render() {
    return (
      <div className="container">
        <Search />
        <JobList jobs={this.state.jobs} />
      </div>
    )
  }
}

export default withAuth(Customer)