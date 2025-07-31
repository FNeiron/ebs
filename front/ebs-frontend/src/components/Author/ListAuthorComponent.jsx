import React, {useEffect, useState} from 'react'
import {deleteAuthor, listAuthors} from '../../services/AuthorService'
import { useNavigate } from 'react-router-dom'

const ListAuthorComponent = () => {

    const [authors, setAuthors] = useState([])

    const navigator = useNavigate();

    useEffect(() => {
        getAllAuthors();
    }, [])

    function getAllAuthors() {
        listAuthors().then((response) => {
            setAuthors(response.data);
        }).catch(error => {
            console.error(error);
        })
    }


    function addNewAuthor() {
        navigator('/add-author')
    }

    function updateAuthor(id) {
        navigator(`/edit-author/${id}`)
    }

    function removeAuthor(id) {

        if(window.confirm("Are you sure you want to delete this author?")) {
         console.log(id)

         deleteAuthor(id).then((response) => {
            getAllAuthors()
         }).catch(error => {
            console.error(error);
         })
    }
}

  return (
    <div className='container'>

        <h2 className='text-center'>List of Authors</h2>
        <button className='btn btn-primary mb-2' onClick= { addNewAuthor }>Add Author</button>
        <table className='table table-striped table-bordered'>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Nickname</th>
                    <th>Birthday</th>
                    <th className='action-column'>Actions</th>
                </tr>
            </thead>
            <tbody>
                {
                    authors.map(author =>
                        <tr key={author.id}>
                            <td>{author.id}</td>
                            <td>{author.name}</td>
                            <td>{author.nickname}</td>
                            <td>{author.birthday}</td>
                            <td>
                                <button className='btn btn-info btn-sm' onClick={() => updateAuthor(author.id)}>Update</button>
                                <button className='btn btn-danger btn-sm' onClick={() => removeAuthor(author.id)}
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

export default ListAuthorComponent