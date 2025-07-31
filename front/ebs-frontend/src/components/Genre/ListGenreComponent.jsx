import React, {useEffect, useState} from 'react'
import {deleteGenre, listGenres} from '../../services/GenreService'
import { useNavigate } from 'react-router-dom'

const ListGenreComponent = () => {

    const [genres, setGenres] = useState([])

    const navigator = useNavigate();

    useEffect(() => {
        getAllGenres();
    }, [])

    function getAllGenres() {
        listGenres().then((response) => {
            setGenres(response.data);
        }).catch(error => {
            console.error(error);
        })
    }


    function addNewGenre() {
        navigator('/add-genre')
    }

    function updateGenre(id) {
        navigator(`/edit-genre/${id}`)
    }

    function removeGenre(id) {

        if(window.confirm("Are you sure you want to delete this genre?")) {
         console.log(id)

         deleteGenre(id).then((response) => {
            getAllGenres()
         }).catch(error => {
            console.error(error);
         })
    }
}

  return (
    <div className='container'>

        <h2 className='text-center'>List of Genres</h2>
        <button className='btn btn-primary mb-2' onClick= { addNewGenre }>Add Genre</button>
        <table className='table table-responsive table-striped table-bordered'>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th className='action-column'>Actions</th>
                </tr>
            </thead>
            <tbody>
                {
                    genres.map(genre =>
                        <tr key={genre.id}>
                            <td>{genre.id}</td>
                            <td>{genre.name}</td>
                            <td>
                                <button className='btn btn-info btn-sm' onClick={() => updateGenre(genre.id)}>Update</button>
                                <button className='btn btn-danger btn-sm' onClick={() => removeGenre(genre.id)}
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

export default ListGenreComponent