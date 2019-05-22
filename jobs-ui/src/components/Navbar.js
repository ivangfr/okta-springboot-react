import React, { Component } from 'react'
import { Link } from "react-router-dom"
import M from 'materialize-css';

class Navbar extends Component {
  componentDidMount() {
    const sidenav = document.querySelectorAll('.sidenav');
    M.Sidenav.init(sidenav);
  }

  render() {
    return (
      <div>
        <div className="navbar-fixed">
        <nav className="blue darken-3">
          <div className="nav-wrapper container">
            <a href="/" className="brand-logo">Jobs Portal</a>
            <a href="/" data-target="mobile-menu" className="sidenav-trigger">
              <i className="material-icons">menu</i>
            </a>
            <ul id="nav" className="right hide-on-med-and-down">
              <li><Link to="/">Home</Link></li>
              <li><Link to="/customer">Customer</Link></li>
              <li><Link to="/staff">Staff</Link></li>
              <li><Link to="/logInOut">Ola Xxx, logInOut</Link></li>
            </ul>
          </div>
        </nav>
        </div>

        <ul id="mobile-menu" className="sidenav">
          <li><Link to="/" className="sidenav-close">Home</Link></li>
          <li><Link to="/customer" className="sidenav-close">Customer</Link></li>
          <li><Link to="/staff" className="sidenav-close">Staff</Link></li>
          <li><Link to="/logInOut">logInOut</Link></li>
        </ul>
      </div>
    )
  }
}

export default Navbar