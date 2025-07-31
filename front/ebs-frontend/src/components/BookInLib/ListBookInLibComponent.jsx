import React, {useEffect, useState} from 'react'
import {deleteBookInLib, listBooksInLib} from '../../services/BookInLibService'
import { listOpenedJournal, returnBook } from '../../services/JournalService'
import { useNavigate } from 'react-router-dom'

const ListBookInLibComponent = () => {

    const [booksInLib, setBooksInLib] = useState([])
    const [journal, setJournal] = useState([])

    const navigator = useNavigate();

    useEffect(() => {
        getAllBooksInLib();
        getAllOpenedJournal();
    }, [])

    function getAllBooksInLib() {
        listBooksInLib().then((response) => {
            setBooksInLib(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function getAllOpenedJournal() {
        listOpenedJournal().then((response) => {
            setJournal(response.data);
        }).catch(error => {
            console.error(error);
        })
    }

    function addNewBookInLib() {
        navigator('/add-book-in-lib')
    }

    function debtBook(id) {
        navigator(`/debt-book/${id}`)
    }

    function getReader(id) {
        const record = journal.find((record) => record.book.id === id)
        return record ? [record.id, record.reader.name] : [null, null]
    }

    function returnBookToLib(journanlId) {
        if (window.confirm("Are you sure you want to return this book?")) {
            console.log(journanlId);
            returnBook(journanlId).then((response) => {
                // Перезагружаем данные о книгах и читателях
                getAllBooksInLib();
                getAllOpenedJournal();
            }).catch(error => {
                console.error(error);
            });
        }
    }
    

    function updateBookInLib(id) {
        navigator(`/edit-book-in-lib/${id}`)
    }

    function removeBookInLib(id) {

        if(window.confirm("Are you sure you want to delete this book?")) {
         console.log(id)

         deleteBookInLib(id).then((response) => {
            getAllBooksInLib()
         }).catch(error => {
            console.error(error);
         })
    }
}

  return (
    <div className='container'>

        <h2 className='text-center'>List of Books In Library</h2>
        <button className='btn btn-primary mb-2' onClick= { addNewBookInLib }>Add Book to the Library</button>
        <table className='table table-striped table-bordered'>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>ISBN</th>
                    <th>Publication Date</th>
                    <th>Reader</th>
                    <th className='action-column'>Actions</th>
                </tr>
            </thead>
            <tbody>
                {
                    booksInLib.map(bookInLib => {
                        const [journalId, readerName] = getReader(bookInLib.id);
                        const rowClass = readerName ? "table-danger" : "table-success";
                        const actionText = readerName ? "Вернуть" : "Выдать";

                        return (
                            <tr className={rowClass} key={bookInLib.id}>
                                <td>{bookInLib.id}</td>
                                <td>{bookInLib.book.name}</td>
                                <td>{bookInLib.book.isbn}</td>
                                <td>{bookInLib.book.publicationDate}</td>
                                <td>{readerName}</td>
                                <td>
                                    <div className='text-center'>
                                    <button className='btn btn-warning btn-sm' onClick={() => journalId
                                        ? returnBookToLib(journalId)
                                        : debtBook(bookInLib.id)}>{actionText}</button>
                                    <button className='btn btn-info btn-sm' onClick={() => updateBookInLib(bookInLib.id)} style={{marginLeft: '10px'}}>Update</button>
                                    <button className='btn btn-danger btn-sm' onClick={() => removeBookInLib(bookInLib.id)} style={{marginLeft: '10px'}}>Delete</button>
                                    </div>
                                </td>
                            </tr>
                        )
                    })
                }
            </tbody>
        </table>
    </div>
  )
}

export default ListBookInLibComponent