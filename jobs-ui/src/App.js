import React from 'react'
import { BrowserRouter, Route } from "react-router-dom"
import Navbar from './components/Navbar'
import Home from './components/Home'
import Customer from './components/customer/Customer'
import Staff from './components/staff/Staff'
import Job from './components/customer/Job'

function App() {
  return (
    <BrowserRouter>
      <div className="App">
        <Navbar />
        <Route path='/' exact component={Home} />
        <Route path='/customer' component={Customer} />
        <Route path='/staff' component={Staff} />
        <Route path='/jobs/:job_id' component={Job} />
      </div>
    </BrowserRouter>
  );
}

export default App;
