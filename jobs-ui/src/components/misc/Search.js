import React, { Component } from 'react'

class Search extends Component {
  state = {
    searchText: ''
  }

  handleChange = (e) => {
    const { id, value } = e.target
    this.setState({
      [id]: value
    })
  }

  handleEnterPressed = (e) => {
    if (e.key === 'Enter') {
      this.props.searchJob(this.state.searchText)
    }
  }

  render() {
    return (
      <div>
        <div className="row">
          <div className="col s12">
            <div className="input-field">
              <i className="material-icons prefix">search</i>
              <input
                id="searchText"
                type="text"
                placeholder='Type your search query here and press enter. "java developer" AND (google OR facebook) NOT junior'
                onChange={this.handleChange}
                onKeyPress={this.handleEnterPressed}
              />
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default Search