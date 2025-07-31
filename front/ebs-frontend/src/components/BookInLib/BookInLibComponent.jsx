import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { createBookInLib, getBookInLib, updateBookInLib } from '../../services/BookInLibService'
import { listBooks } from '../../services/BookService'
import { MultiSelect } from 'react-multi-select-component'

const BookInLibComponent = () => {

    const [book, setBook] = useState([])
    const [availableBooks, setAvailableBooks] = useState([])

    const [errors, setErrors] = useState({
        book: ''
    })

    const { id } = useParams()
    const navigator = useNavigate()

    useEffect(() => {
        listBooks().then((response) => {
            setAvailableBooks(response.data)
        }).catch(error => {
            console.error('Error fetching books:', error)
        })
    }, [])

    function saveOrUpdateBookInLib(e) {
        e.preventDefault();

        if(validateForm()) {

            const bookInLib = {
                book: {
                    id: book
                }
            }

            console.log(bookInLib)

            if(id) {
                updateBookInLib(id, bookInLib).then((response) => {
                    console.log(response.data);
                    navigator('/books-in-lib')
                }).catch(error => {
                    console.error(error);
                })
            } else {
                createBookInLib(bookInLib).then((response) => {
                    console.log(response.data)
                    navigator('/books-in-lib')
                }).catch(error => {
                    console.log(error);
                })
            }
        }
    }

    function cancelForm(e) {
        e.preventDefault();
        navigator('/books-in-lib')
    }

    function validateForm() {
        let valid = true;

        const errorsCopy = {... errors}

        if(book) {
            errorsCopy.book = '';
        } else {
            errorsCopy.book = 'Book is required';
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
                            <label htmlFor="book" className="form-label">Book:</label>
                            <select
                                name="book"
                                value={book.id}
                                className={`form-control ${errors.book ? 'is-invalid' : ''}`}
                                onChange={(e) => setBook(e.target.value)}
                            >
                                <option value="">Select Book</option>
                                {availableBooks.map(b => (
                                    <option key={b.id} value={b.id}>{b.name}</option>
                                ))}
                            </select>
                            {errors.book && <div className='invalid-feedback'>{errors.book}</div>}
                        </div>

                        <button className='btn btn-success' onClick={saveOrUpdateBookInLib} >Submit</button>
                        <button className='btn btn-danger' onClick={cancelForm} >Cancel</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
  )
}

export default BookInLibComponent