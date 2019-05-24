import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import { withAuth } from '@okta/okta-react'
import M from 'materialize-css';

class Navbar extends Component {
  state = {
    authenticated: null
  }

  async componentDidMount() {
    this.checkAuthentication();

    const sidenav = document.querySelectorAll('.sidenav');
    M.Sidenav.init(sidenav);
  }

  async componentDidUpdate() {
    this.checkAuthentication();
  }

  checkAuthentication = async () => {
    const authenticated = await this.props.auth.isAuthenticated();
    if (authenticated !== this.state.authenticated) {
      this.setState({
        authenticated
      });
    }
  }

  logHandleLogInOut = async () => {
    this.state.authenticated ? this.props.auth.logout('/') : this.props.auth.login('/')
  }

  render() {
    console.log(this.props)
    console.log(this.state)
    const logInOut = this.state.authenticated ? "Logout" : "Login"

    return (
      <div>
        <div className="navbar-fixed">
          <nav className="light-blue darken-4">
            <div className="nav-wrapper container">
              <a href="/" className="brand-logo">Jobs Portal</a>
              <a href="/" data-target="mobile-menu" className="sidenav-trigger">
                <i className="material-icons">menu</i>
              </a>
              <ul id="nav" className="right hide-on-med-and-down">
                <li><Link to="/">Home</Link></li>
                <li><Link to="/customer">Customer</Link></li>
                <li><Link to="/staff">Staff</Link></li>
                <li><Link to="/" onClick={this.logHandleLogInOut}>{logInOut}</Link></li>
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

export default withAuth(Navbar)