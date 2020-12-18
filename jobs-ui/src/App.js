import React from 'react'
import { BrowserRouter as Router, Route } from 'react-router-dom'
import { OktaAuth } from '@okta/okta-auth-js'
import { Security, SecureRoute, LoginCallback } from '@okta/okta-react'
import Navbar from './components/misc/Navbar'
import Home from './components/home/Home'
import Customer from './components/customer/Customer'
import JobView from './components/customer/JobView'
import Staff from './components/staff/Staff'
import JobForm from './components/staff/JobForm'

function App() {
  const oktaAuth = new OktaAuth({
    issuer: `${process.env.REACT_APP_OKTA_ORG_URL}/oauth2/default`,
    clientId: process.env.REACT_APP_OKTA_CLIENT_ID,
    redirectUri: `${window.location.origin}/implicit/callback`
  })
  return (
    <Router>
      <Security oktaAuth={oktaAuth} >
        <div className="App">
          <Navbar />
          <Route path='/' exact component={Home} />
          <Route path='/login' exact component={Home} />
          <SecureRoute path='/customer' exact component={Customer} />
          <SecureRoute path='/staff' exact component={Staff} />
          <SecureRoute path='/jobs/:job_id' component={JobView} />
          <SecureRoute path='/staff/jobs' exact component={JobForm} />
          <SecureRoute path='/staff/jobs/:job_id' component={JobForm} />
          <Route path='/implicit/callback' component={LoginCallback} />
        </div>
      </Security>
    </Router>
  )
}

export default App
