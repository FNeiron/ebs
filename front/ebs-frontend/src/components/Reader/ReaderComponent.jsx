import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { createReader, getReader, updateReader } from '../../services/ReaderService'

const ReaderComponent = () => {

    const [name, setName] = useState('')
    const [email, setEmail] = useState('')

    const [errors, setErrors] = useState({
        name: '',
        email: ''
    })

    const {id} = useParams()
    const navigator = useNavigate()

    useEffect(() => {
        if(id) {
            getReader(id).then((response) => {
                setName(response.data.name);
                setEmail(response.data.email);
            }).catch(error => {
                console.error(error);
            })
        }
    }, [])

    function saveOrUpdateReader(e) {
        e.preventDefault();

        if(validateForm()) {

            const reader = {name, email}
            console.log(reader)


            if(id) {
                updateReader(id, reader).then((response) => {
                    console.log(response.data);
                    navigator('/readers')
                }).catch(error => {
                    console.error(error);
                })
            } else {
                createReader(reader).then((response) => {
                    console.log(response.data)
                    navigator('/readers')
                }).catch(error => {
                    console.log(error);
                })
            }
        }
    }

    function cancelForm(e) {
        e.preventDefault();
        navigator('/readers')
    }

    function validateForm() {
        let valid = true;

        const errorsCopy = {... errors}

        if(name.trim()) {
            errorsCopy.name = '';
        } else {
            errorsCopy.name = 'Name is required';
            valid = false;
        }

        if(email.trim()) {
            errorsCopy.email = '';
        } else {
            errorsCopy.email = 'Email is required';
            valid = false;
        }

        setErrors(errorsCopy);

        return valid;
    }

    function pageTitle() {
        if(id) {
            return <h2 className='text-center'>Update Reader</h2>
        } else {
            return <h2 className='text-center'>Add Reader</h2>
        }
    }

  return (
    <div className='container'>
        <br /> <br />
        <div className='row'>
            <div className='card col-md-6 offset-md-3 offset-md-3'>
                {
                    pageTitle()
                }
                <div className='card-body'>
                    <form>
                        <div className='form-group mb-2'>
                            <label htmlFor="form-label">Name:</label>
                            <input
                            type="text"
                            placeholder='Enter Reader Name'
                            name='name'
                            value={name}
                            className={`form-control ${errors.name ? 'is-invalid': ''}`}
                            onChange={(e) => setName(e.target.value)}
                            >
                            </input>
                            {errors.name && <div className='invalid-feedback'>{errors.name}</div>}
                        </div>

                        <div className='form-group mb-2'>
                            <label htmlFor="form-label">Email:</label>
                            <input
                            type="email"
                            placeholder='Enter Reader Email'
                            name='email'
                            value={email}
                            className={`form-control ${errors.email ? 'is-invalid': ''}`}
                            onChange={(e) => setEmail(e.target.value)}
                            >
                            </input>
                            {errors.email && <div className='invalid-feedback'>{errors.email}</div>}
                        </div>

                        <button className='btn btn-success' onClick={saveOrUpdateReader} >Submit</button>
                        <button className='btn btn-danger' onClick={cancelForm} >Cancel</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
  )
}

export default ReaderComponent