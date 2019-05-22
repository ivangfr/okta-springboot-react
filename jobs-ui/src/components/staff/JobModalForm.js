import React, { Component } from 'react'
import M from 'materialize-css';

class JobModalForm extends Component {
  state = {
    id: '',
    title: '',
    description: ''
  }

  handleChange = (e) => {
    const { id, value } = e.target
    this.setState({
      [id]: value
    });
  }

  handleSave = (e) => {
    const title = this.state.title.trim();
    const description = this.state.description.trim();
    if (title && description ) {
      this.props.saveJob(this.state)
    } else {
      e.preventDefault()
    }
    this.reset()
  }

  handleCancel = () => {
    this.reset()
  }

  reset = () => {
    this.setState({
      id: '',
      title: '',
      description: ''
    })
  }

  componentDidMount() {
    const modal = document.querySelectorAll('.modal');
    M.Modal.init(modal, {
      dismissible: false
    });

    const characterCounter = document.querySelectorAll('input, textarea')
    M.CharacterCounter.init(characterCounter);
  }

  componentWillReceiveProps(props) {
    const updatingJob = props.updatingJob;
    const id = updatingJob ? updatingJob.id : ''
    const title = updatingJob ? updatingJob.title : ''
    const description = updatingJob ? updatingJob.description : ''
    this.setState({
      id,
      title,
      description
    })
  }

  componentDidUpdate() {
    // It is needed  to avoid labels overlapping prefilled content 
    // Besides, the labels of this form component have "active" className
    M.updateTextFields();
  }

  render() {
    return (
      <div id="modal" className="modal">
        <div className="modal-content">
          <div className="row">
            <form className="col s12">
              <div className="row">
                <h4>Job</h4>
              </div>
              <div className="row">
                <div className="input-field col s12">
                  <input id="title" type="text" data-length="50" className="validate" onChange={this.handleChange} value={this.state.title}/>
                  <label htmlFor="title" className="active">Title</label>
                </div>
              </div>
              <div className="row">
                <div className="input-field col s12">
                  <textarea id="description" className="materialize-textarea validate" data-length="500" onChange={this.handleChange} value={this.state.description}></textarea>
                  <label htmlFor="description" className="active">Description</label>
                </div>
              </div>
            </form>
          </div>
        </div>
        <div className="modal-footer">
          <button className="modal-close waves-effect waves-green btn-flat" onClick={this.handleCancel}>Cancel</button>
          <button className="modal-close waves-effect waves-green btn-flat" onClick={this.handleSave}>Save</button>
        </div>
      </div>
    )
  }
}

export default JobModalForm;