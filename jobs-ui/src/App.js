import React from 'react'
import { BrowserRouter, Route } from "react-router-dom"
import Navbar from './components/Navbar'
import Home from './components/Home'
import Customer from './components/customer/Customer'
import JobView from './components/customer/JobView'
import Staff from './components/staff/Staff'
import JobForm from './components/staff/JobForm'

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Navbar />
        <Route path='/' exact component={Home} />
        <Route path='/customer' exact component={Customer} />
        <Route path='/staff' exact component={Staff} />
        <Route path='/jobs/:job_id' component={JobView} />
        <Route path='/staff/jobs' exact component={JobForm} />
        <Route path='/staff/jobs/:job_id' component={JobForm} />
      </div>
    </BrowserRouter>
  );
}

export default App;
