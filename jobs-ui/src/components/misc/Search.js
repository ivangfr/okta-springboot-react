import React, { Component } from 'react'

class Search extends Component {
  render() {
    return (
      <div>
        <div className="row">
          <div className="col s12">
            <div className="input-field">
              <i className="material-icons prefix">search</i>
              <input id="search" type="text" required />
              <label htmlFor="search">Search jobs</label>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default Search