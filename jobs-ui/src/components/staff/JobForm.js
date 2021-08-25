import React, { Component } from 'react'
import { withOktaAuth } from '@okta/okta-react'
import M from 'materialize-css'
import JobCardHome from '../home/JobCard'
import JobCardCustomer from '../customer/JobCard'
import API from '../misc/api'

class JobForm extends Component {
  state = {
    id: '',
    title: '',
    company: '',
    logoUrl: '',
    description: '',
    createDate: ''
  }

  handleChange = (e) => {
    const { id, value } = e.target
    this.setState({
      [id]: value
    })
  }

  redirectJobList = () => {
    this.props.history.push("/staff")
  }

  async componentDidMount() {
    const id = this.props.match.params.job_id
    if (id) {
      API.get(`jobs/${id}`, {
        headers: {
          'Authorization': 'Bearer ' + await this.props.authState.accessToken.accessToken
        }
      })
        .then(response => {
          const job = response.data
          this.setState({
            id: job.id,
            title: job.title,
            company: job.company,
            logoUrl: job.logoUrl,
            description: job.description,
            createDate: job.createDate
          })
        })
        .catch(error => {
          console.log(error)
          M.toast({html: error, classes: 'rounded'})
        })
    }

    M.Tabs.init(document.querySelectorAll('.tabs'))
  }

  saveJob = async () => {
    if (!this.validateForm()) {
      return
    }

    const job = this.state
    let method = 'POST'
    let url = 'http://localhost:8080/api/jobs'
    if (job.id) {
      method = 'PUT'
      url += '/' + job.id
    }

    API.request({
      method: method,
      url: url,
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer ' + await this.props.authState.accessToken.accessToken
      },
      data: JSON.stringify(job)
    })
      .then(() => {
        this.redirectJobList()
      })
      .catch(error => {
        console.log(error)
        M.toast({html: error, classes: 'rounded'})
      })
  }

  validateForm = () => {
    const fields = document.querySelectorAll(".validate")
    for (let i = 0; i < fields.length; i++) {
      if (fields[i].value.trim() === "") {
        document.getElementById(fields[i].id).focus()
        return false
      }
    }
    return true
  }

  componentDidUpdate() {
    // It is needed to avoid labels overlapping prefilled content 
    // Besides, the labels of this form component have "active" className
    M.updateTextFields()

    // It is needed otherwise, on editing, the textarea will start with
    // just 2 lines
    M.textareaAutoResize(document.querySelector('.materialize-textarea'))
  }

  mockJobIdAndCreateDate = () => {
    let job = { ...this.state }
    job.id = 'XXXXXXXXXXXXXXXXXXXXXXXX'
    job.createDate = new Date()
    return job
  }

  render() {
    const job = this.state.id ? this.state : this.mockJobIdAndCreateDate()
    const idFieldVisibility = this.state.id ? { display: "block" } : { display: "none" }

    const form = (
      <div className="row">
        <form className="col s12">
          <div className="row" style={idFieldVisibility}>
            <div className="input-field col s12">
              <input disabled value={job.id} id="id" type="text" />
              <label htmlFor="id">Id</label>
            </div>
          </div>
          <div className="row">
            <div className="input-field col s12">
              <input required className="validate" value={job.title} id="title" type="text" onChange={this.handleChange} />
              <span className="helper-text" data-error="Title cannot be empty"></span>
              <label htmlFor="title">Title</label>
            </div>
          </div>
          <div className="row">
            <div className="input-field col s12">
              <input required className="validate" value={job.company} id="company" type="text" onChange={this.handleChange} />
              <span className="helper-text" data-error="Company cannot be empty"></span>
              <label htmlFor="company">Company</label>
            </div>
          </div>
          <div className="row">
            <div className="input-field col s12">
              <input value={job.logoUrl} id="logoUrl" type="text" onChange={this.handleChange} />
              <label htmlFor="logoUrl">Logo Url</label>
            </div>
          </div>
          <div className="row">
            <div className="input-field col s12">
              <textarea required className="materialize-textarea validate" id="description" onChange={this.handleChange} value={this.state.description}></textarea>
              <span className="helper-text" data-error="Description cannot be empty"></span>
              <label htmlFor="description" className="active">Description</label>
            </div>
          </div>
        </form>
        <div className="row">
          <div className="input-field col s12">
            <button className="waves-effect waves-green btn-flat right" onClick={this.saveJob}>Save</button>
            <button className="waves-effect waves-green btn-flat right" onClick={this.redirectJobList}>Cancel</button>
          </div>
        </div>
      </div>
    )

    return (
      <div className="container">
        <div className="row">
          <div className="col s12">
            <ul className="tabs">
              <li className="tab col s4"><a className="active" href="#form">Form</a></li>
              <li className="tab col s4"><a href="#home-card-preview">Home Card Preview</a></li>
              <li className="tab col s4"><a href="#customer-card-preview">Customer Card Preview</a></li>
            </ul>
          </div>
          <div id="form" className="col s12">
            {form}
          </div>
          <div id="home-card-preview" className="col s12">
            <JobCardHome job={job} />
          </div>
          <div id="customer-card-preview" className="col s12">
            <JobCardCustomer job={job} />
          </div>
        </div>
      </div>
    )
  }
}

export default withOktaAuth(JobForm)