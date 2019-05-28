import React, { Component } from 'react'

class Search extends Component {
  state = {
    id: ''
  }

  handleChange = (e) => {
    const { id, value } = e.target
    this.setState({
      [id]: value
    })
  }

  handleEnterPressed = (e) => {
    if (e.key === 'Enter') {
      this.props.searchJob(this.state.id)
    }
  }

  render() {
    return (
      <div>
        <div className="row">
          <div className="col s12">
            <div className="input-field">
              <i className="material-icons prefix">search</i>
              <input id="id" type="text" onChange={this.handleChange} onKeyPress={this.handleEnterPressed} />
              <label htmlFor="id">Search jobs</label>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default Search