import React, {useEffect, useState} from 'react'
import {deleteReader, listReaders} from '../../services/ReaderService'
import { useNavigate } from 'react-router-dom'

const ListReaderComponent = () => {

    const [readers, setReaders] = useState([])

    const navigator = useNavigate();

    useEffect(() => {
        getAllReaders();
    }, [])

    function getAllReaders() {
        listReaders().then((response) => {
            setReaders(response.data);
        }).catch(error => {
            console.error(error);
        })
    }


    function addNewReader() {
        navigator('/add-reader')
    }

    function updateReader(id) {
        navigator(`/edit-reader/${id}`)
    }

    function removeReader(id) {

        if(window.confirm("Are you sure you want to delete this reader?")) {
         console.log(id)

         deleteReader(id).then((response) => {
            getAllReaders()
         }).catch(error => {
            console.error(error);
         })
    }
}

  return (
    <div className='container'>

        <h2 className='text-center'>List of Readers</h2>
        <button className='btn btn-primary mb-2' onClick= { addNewReader }>Add Reader</button>
        <table className='table table-striped table-bordered'>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th className='action-column'>Actions</th>
                </tr>
            </thead>
            <tbody>
                {
                    readers.map(reader =>
                        <tr key={reader.id}>
                            <td>{reader.id}</td>
                            <td>{reader.name}</td>
                            <td>{reader.email}</td>
                            <td>
                                <button className='btn btn-info btn-sm' onClick={() => updateReader(reader.id)}>Update</button>
                                <button className='btn btn-danger btn-sm' onClick={() => removeReader(reader.id)}
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

export default ListReaderComponent