import React, { Component } from 'react'
import { Link, NavLink } from 'react-router-dom'
import { withOktaAuth } from '@okta/okta-react'
import M from 'materialize-css'

class Navbar extends Component {
  state = {
    authenticated: null,
    user: null
  }

  async componentDidMount() {
    this.checkAuthentication()

    const sidenav = document.querySelectorAll('.sidenav')
    M.Sidenav.init(sidenav)
  }

  async componentDidUpdate() {
    this.checkAuthentication()
  }

  checkAuthentication = async () => {
    const authenticated = await this.props.authState.isAuthenticated
    if (authenticated !== this.state.authenticated) {
      const user = await this.props.authService.getUser()
      this.setState({ authenticated, user })
    }
  }

  logHandleLogInOut = async () => {
    this.state.authenticated ? this.props.authService.logout('/') : this.props.authService.login('/')
  }

  render() {
    const linkVisibility = this.state.authenticated ? { "display": "block" } : { "display": "none" }
    const username = this.state.authenticated && this.state.user.preferred_username
    const logInOut = this.state.authenticated ? "Logout" : "Login"
    return (
      <div>
        <div className="navbar-fixed">
          <nav className="light-blue darken-4">
            <div className="nav-wrapper container">
              <Link to="/" className="brand-logo">Jobs Portal</Link>
              <a href="/" data-target="mobile-menu" className="sidenav-trigger">
                <i className="material-icons">menu</i>
              </a>
              <ul id="nav" className="right hide-on-med-and-down">
                <li>{username}</li>
                <li><NavLink exact to="/">Home</NavLink></li>
                <li><NavLink exact to="/customer" style={linkVisibility}>Customer</NavLink></li>
                <li><NavLink exact to="/staff" style={linkVisibility}>Staff</NavLink></li>
                <li><NavLink exact to="/login" onClick={this.logHandleLogInOut}>{logInOut}</NavLink></li>
              </ul>
            </div>
          </nav>
        </div>

        <ul id="mobile-menu" className="sidenav">
          <li><Link to="/" className="sidenav-close">Home</Link></li>
          <li><Link to="/customer" className="sidenav-close">Customer</Link></li>
          <li><Link to="/staff" className="sidenav-close">Staff</Link></li>
          <li><Link to="/" onClick={this.logHandleLogInOut}>{logInOut}</Link></li>
        </ul>
      </div>
    )
  }
}

export default withOktaAuth(Navbar)