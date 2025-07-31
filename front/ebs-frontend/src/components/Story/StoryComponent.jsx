import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { createStory, getStory, updateStory } from '../../services/StoryService'
import { listGenres } from '../../services/GenreService'
import { listAuthors } from '../../services/AuthorService'
import { MultiSelect } from 'react-multi-select-component'

const StoryComponent = () => {

    const [name, setName] = useState('')
    const [genre, setGenre] = useState('')
    const [authors, setAuthors] = useState([])
    const [availableGenres, setAvailableGenres] = useState([])
    const [availableAuthors, setAvailableAuthors] = useState([])

    const [errors, setErrors] = useState({
        name: '',
        genre: '',
        authors: ''
    })

    const { id } = useParams()
    const navigator = useNavigate()

    useEffect(() => {
        if (id) {
            getStory(id).then((response) => {
                setName(response.data.name)
                setGenre(response.data.genre)
                setAuthors(response.data.authors.map(author => ({ label: author.name, value: author.id })))
            }).catch(error => {
                console.error(error)
            })
        }
    }, [])

    useEffect(() => {
        listGenres().then((response) => {
            setAvailableGenres(response.data)
        }).catch(error => {
            console.error('Error fetching genres:', error)
        })

        listAuthors().then((response) => {
            const formattedAuthors = response.data.map(author => ({
                label: author.name,
                value: author.id
            }))
            setAvailableAuthors(formattedAuthors)
        }).catch(error => {
            console.error('Error fetching authors:', error)
        })
    }, [])

    function saveOrUpdateStory(e) {
        e.preventDefault();

        if(validateForm()) {

            const story = {
                name,
                genre: {
                    id: genre
                },
                authors: authors.map(author => ({ id: author.value })) // Создаем массив объектов с id авторов
            };
            console.log(story)


            if(id) {
                updateStory(id, story).then((response) => {
                    console.log(response.data);
                    navigator('/stories')
                }).catch(error => {
                    console.error(error);
                })
            } else {
                createStory(story).then((response) => {
                    console.log(response.data)
                    navigator('/stories')
                }).catch(error => {
                    console.log(error);
                })
            }
        }
    }

    function cancelForm(e) {
        e.preventDefault();
        navigator('/stories')
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

        if(genre) {
            errorsCopy.genre = '';
        } else {
            errorsCopy.genre = 'Genre is required';
            valid = false;
        }

        if(authors.length > 0) {
            errorsCopy.authors = '';
        } else {
            errorsCopy.authors = 'At least one author is required';
            valid = false;
        }

        setErrors(errorsCopy);

        return valid;
    }

    function pageTitle() {
        if(id) {
            return <h2 className='text-center'>Update Story</h2>
        } else {
            return <h2 className='text-center'>Add Story</h2>
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
                            placeholder='Enter Story Name'
                            name='name'
                            value={name}
                            className={`form-control ${errors.name ? 'is-invalid': ''}`}
                            onChange={(e) => setName(e.target.value)}
                            >
                            </input>
                            {errors.name && <div className='invalid-feedback'>{errors.name}</div>}
                        </div>

                        <div className='form-group mb-2'>
                            <label htmlFor="genre" className="form-label">Genre:</label>
                            <select
                                name="genre"
                                value={genre.id}
                                className={`form-control ${errors.genre ? 'is-invalid' : ''}`}
                                onChange={(e) => setGenre(e.target.value)}
                            >
                                <option value="">Select Genre</option>
                                {availableGenres.map(g => (
                                    <option key={g.id} value={g.id}>{g.name}</option>
                                ))}
                            </select>
                            {errors.genre && <div className='invalid-feedback'>{errors.genre}</div>}
                        </div>

                        <div className='form-group mb-2'>
                                <label htmlFor="authors" className="form-label">Authors:</label>
                                <MultiSelect
                                    options={availableAuthors}
                                    value={authors}
                                    onChange={setAuthors}
                                    labelledBy="Select Authors"
                                    className={errors.authors ? 'is-invalid' : ''}
                                />
                            {errors.authors && <div className='invalid-feedback'>{errors.authors}</div>}
                        </div>

                        <button className='btn btn-success' onClick={saveOrUpdateStory} >Submit</button>
                        <button className='btn btn-danger' onClick={cancelForm} >Cancel</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
  )
}

export default StoryComponent