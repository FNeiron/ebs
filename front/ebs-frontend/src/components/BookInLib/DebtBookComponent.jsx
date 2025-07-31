import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { listReaders } from '../../services/ReaderService'
import { debtBook } from '../../services/JournalService'

const DebtBookComponent = () => {

    const [readerId, setReader] = useState([])
    const [readers, setReaders] = useState([])

    const [errors, setErrors] = useState({
        readerId: ''
    })

    const { id } = useParams()
    const navigator = useNavigate()

    useEffect(() => {
        listReaders().then((response) => {
            setReaders(response.data)
        }).catch(error => {
            console.error('Error fetching readers:', error)
        })
    }, [])

    function debtBookToReader(e) {
        e.preventDefault();

        if(validateForm()) {

            const reader = readerId

            console.log(reader)

            if(id) {
                debtBook(id, reader).then((response) => {
                    console.log(response.data);
                    navigator('/books-in-lib')
                }).catch(error => {
                    console.error(error);
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

        if(readerId) {
            errorsCopy.readerId = '';
        } else {
            errorsCopy.readerId = 'Reader is required';
            valid = false;
        }

        setErrors(errorsCopy);

        return valid;
    }

  return (
    <div className='container'>
        <br /> <br />
        <div className='row'>
            <div className='card col-md-6 offset-md-3 offset-md-3'>
            <h2 className='text-center'>Choose a reader</h2>
                <div className='card-body'>
                    <form>
                    <div className='form-group mb-2'>
                            <label htmlFor="book" className="form-label">Book:</label>
                            <select
                                name="book"
                                className={`form-control ${errors.readerId ? 'is-invalid' : ''}`}
                                onChange={(e) => setReader(e.target.value)}
                            >
                                <option value="">Select Reader</option>
                                {readers.map(r => (
                                    <option key={r.id} value={r.id}>{r.name}</option>
                                ))}
                            </select>
                            {errors.readerId && <div className='invalid-feedback'>{errors.readerId}</div>}
                        </div>

                        <button className='btn btn-success' onClick={debtBookToReader} >Submit</button>
                        <button className='btn btn-danger' onClick={cancelForm} >Cancel</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
  )
}

export default DebtBookComponent