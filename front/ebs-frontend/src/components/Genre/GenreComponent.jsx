import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { createGenre, getGenre, updateGenre } from '../../services/GenreService'

const GenreComponent = () => {

    const [name, setGenre] = useState('')

    const [errors, setErrors] = useState({
        name: ''
    })

    const {id} = useParams()
    const navigator = useNavigate()

    useEffect(() => {
        if(id) {
            getGenre(id).then((response) => {
                setGenre(response.data.name);
            }).catch(error => {
                console.error(error);
            })
        }
    }, [])

    function saveOrUpdateGenre(e) {
        e.preventDefault();

        if(validateForm()) {

            const genre = {name}
            console.log(genre)


            if(id) {
                updateGenre(id, genre).then((response) => {
                    console.log(response.data);
                    navigator('/genres')
                }).catch(error => {
                    console.error(error);
                })
            } else {
                createGenre(genre).then((response) => {
                    console.log(response.data)
                    navigator('/genres')
                }).catch(error => {
                    console.log(error);
                })
            }
        }
    }

    function cancelForm(e) {
        e.preventDefault();
        navigator('/genres')
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

        setErrors(errorsCopy);

        return valid;
    }

    function pageTitle() {
        if(id) {
            return <h2 className='text-center'>Update Genre</h2>
        } else {
            return <h2 className='text-center'>Add Genre</h2>
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
                            placeholder='Enter Genre Name'
                            name='name'
                            value={name}
                            className={`form-control ${errors.name ? 'is-invalid': ''}`}
                            onChange={(e) => setGenre(e.target.value)}
                            >
                            </input>
                            {errors.name && <div className='invalid-feedback'>{errors.name}</div>}
                        </div>

                        <button className='btn btn-success' onClick={saveOrUpdateGenre} >Submit</button>
                        <button className='btn btn-danger' onClick={cancelForm} >Cancel</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
  )
}

export default GenreComponent