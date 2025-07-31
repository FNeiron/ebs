import React, {useEffect, useState} from 'react'
import {deleteBook, listBooks} from '../../services/BookService'
import { useNavigate } from 'react-router-dom'

const ListBookComponent = () => {

    const [books, setBooks] = useState([])

    const navigator = useNavigate();

    useEffect(() => {
        getAllBooks();
    }, [])

    function getAllBooks() {
        listBooks().then((response) => {
            setBooks(response.data);
        }).catch(error => {
            console.error(error);
        })
    }


    function addNewBook() {
        navigator('/add-book')
    }

    function updateBook(id) {
        navigator(`/edit-book/${id}`)
    }

    function removeBook(id) {

        if(window.confirm("Are you sure you want to delete this book?")) {
         console.log(id)

         deleteBook(id).then((response) => {
            getAllBooks()
         }).catch(error => {
            console.error(error);
         })
    }
}

  return (
    <div className='container'>

        <h2 className='text-center'>List of Books</h2>
        <button className='btn btn-primary mb-2' onClick= { addNewBook }>Add Book</button>
        <table className='table table-striped table-bordered'>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>ISBN</th>
                    <th>Publication Date</th>
                    <th className='action-column'>Actions</th>
                </tr>
            </thead>
            <tbody>
                {
                    books.map(book =>
                        <tr key={book.id}>
                            <td>{book.id}</td>
                            <td>{book.name}</td>
                            <td>{book.isbn}</td>
                            <td>{book.publicationDate}</td>
                            <td>
                                <button className='btn btn-info btn-sm' onClick={() => updateBook(book.id)}>Update</button>
                                <button className='btn btn-danger btn-sm' onClick={() => removeBook(book.id)}
                                    style={{marginLeft: '10px'}}
                                    >Delete</button>
                            </td>
                        </tr>)
                }
            </tbody>
        </table>
    </div>
  )
}

export default ListBookComponent