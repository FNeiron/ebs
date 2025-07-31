import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { createBook, getBook, updateBook } from '../../services/BookService'
import { listStories } from '../../services/StoryService'
import { MultiSelect } from 'react-multi-select-component'

const BookComponent = () => {

    const [name, setName] = useState('')
    const [isbn, setIsbn] = useState('')
    const [stories, setStories] = useState([])
    const [publicationDate, setPublicationDate] = useState('')
    const [availableStories, setAvailableStories] = useState([])

    const [errors, setErrors] = useState({
        name: '',
        isbn: '',
        publicationDate: '',
        stories: ''
    })

    const { id } = useParams()
    const navigator = useNavigate()

    useEffect(() => {
        if (id) {
            getBook(id).then((response) => {
                setName(response.data.name)
                setIsbn(response.data.isbn)
                setPublicationDate(response.data.publicationDate)
                setStories(response.data.stories.map(story => ({ label: story.name, value: story.id })))
            }).catch(error => {
                console.error(error)
            })
        }
    }, [])

    useEffect(() => {
        listStories().then((response) => {
            const formattedStories = response.data.map(story => ({
                label: story.name,
                value: story.id
            }))
            setAvailableStories(formattedStories)
        }).catch(error => {
            console.error('Error fetching stories:', error)
        })
    }, [])

    function saveOrUpdateBook(e) {
        e.preventDefault();

        if(validateForm()) {

            const book = {
                name,
                isbn,
                publicationDate,
                stories: stories.map(story => ({ id: story.value })) // Создаем массив объектов с id авторов
            };
            console.log(book)


            if(id) {
                updateBook(id, book).then((response) => {
                    console.log(response.data);
                    navigator('/books')
                }).catch(error => {
                    console.error(error);
                })
            } else {
                createBook(book).then((response) => {
                    console.log(response.data)
                    navigator('/books')
                }).catch(error => {
                    console.log(error);
                })
            }
        }
    }

    function cancelForm(e) {
        e.preventDefault();
        navigator('/books')
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

        if(isbn.trim()) {
            errorsCopy.isbn = '';
        } else {
            errorsCopy.isbn = 'ISBN is required';
            valid = false;
        }

        if(publicationDate.trim()) {
            errorsCopy.publicationDate = '';
        } else {
            errorsCopy.publicationDate = 'Publication Date is required';
            valid = false;
        }

        if(stories.length > 0) {
            errorsCopy.stories = '';
        } else {
            errorsCopy.stories = 'At least one story is required';
            valid = false;
        }

        setErrors(errorsCopy);

        return valid;
    }

    function pageTitle() {
        if(id) {
            return <h2 className='text-center'>Update Book</h2>
        } else {
            return <h2 className='text-center'>Add Book</h2>
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
                            placeholder='Enter Book Name'
                            name='name'
                            value={name}
                            className={`form-control ${errors.name ? 'is-invalid': ''}`}
                            onChange={(e) => setName(e.target.value)}
                            >
                            </input>
                            {errors.name && <div className='invalid-feedback'>{errors.name}</div>}
                        </div>

                        <div className='form-group mb-2'>
                            <label htmlFor="form-label">ISBN:</label>
                            <input
                            type="text"
                            placeholder='Enter Book ISBN'
                            name='isbn'
                            value={isbn}
                            className={`form-control ${errors.isbn ? 'is-invalid': ''}`}
                            onChange={(e) => setIsbn(e.target.value)}
                            >
                            </input>
                            {errors.isbn && <div className='invalid-feedback'>{errors.isbn}</div>}
                        </div>

                        <div className='form-group mb-2'>
                            <label htmlFor="form-label">Publication Date:</label>
                            <input
                            type="date"
                            placeholder='Enter Book Publication Date'
                            name='publicationDate'
                            value={publicationDate}
                            className={`form-control ${errors.publicationDate ? 'is-invalid': ''}`}
                            onChange={(e) => setPublicationDate(e.target.value)}
                            >
                            </input>
                            {errors.publicationDate && <div className='invalid-feedback'>{errors.publicationDate}</div>}
                        </div>

                        <div className='form-group mb-2'>
                                <label htmlFor="stories" className="form-label">Stories:</label>
                                <MultiSelect
                                    options={availableStories}
                                    value={stories}
                                    onChange={setStories}
                                    labelledBy="Select Stories"
                                    className={errors.stories ? 'is-invalid' : ''}
                                />
                            {errors.stories && <div className='invalid-feedback'>{errors.stories}</div>}
                        </div>

                        <button className='btn btn-success' onClick={saveOrUpdateBook} >Submit</button>
                        <button className='btn btn-danger' onClick={cancelForm} >Cancel</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
  )
}

export default BookComponent