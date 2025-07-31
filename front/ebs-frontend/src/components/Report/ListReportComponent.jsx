import React, { useEffect, useState } from 'react'
import { listJournal } from '../../services/JournalService'
import { useNavigate } from 'react-router-dom'

const ListReportComponent = () => {

    const [books, setBooks] = useState([])
    const [readers, setReaders] = useState([])
    const [journal, setJournal] = useState([])
    const [filteredJournal, setFilteredJournal] = useState([])  // Добавлено состояние для фильтрованных данных
    const [reader, setReader] = useState('')
    const [book, setBook] = useState('')

    const navigator = useNavigate();

    useEffect(() => {
        getAllJournal();
    }, [])

    useEffect(() => {
        // Обновление фильтрованных данных при изменении reader или book
        filterJournal();
    }, [reader, book, journal]);

    function getAllJournal() {
        listJournal().then((response) => {
            setJournal(response.data);
    
            const uniqueReaders = response.data
                .map((v) => v.reader)
                .filter((value, index, self) => 
                    index === self.findIndex((t) => t.id === value.id)
                );
    
            const uniqueBooks = response.data
                .map((v) => v.book.book)
                .filter((value, index, self) => 
                    index === self.findIndex((t) => t.id === value.id)
                );
    
            setReaders(uniqueReaders);
            setBooks(uniqueBooks);
        }).catch((error) => {
            console.error(error);
        });
    }

    function filterJournal() {
        const filtered = journal.filter(record => {
            const readerMatch = reader ? record.reader.id === parseInt(reader) : true;
            const bookMatch = book ? record.book.book.id === parseInt(book) : true;
            return readerMatch && bookMatch;
        });
        setFilteredJournal(filtered);
    }

    return (
        <div className='container'>

            <h2 className='text-center'>List of Books In Library</h2>

            <div className='form-row d-flex mb-2'>
                <div className='form-group me-2' style={{ flex: 1 }}>
                    <label htmlFor="book" className="form-label">Book In The Library:</label>
                    <select
                        className='form-select'
                        name="book"
                        onChange={(e) => {setBook(e.target.value); console.log(e.target.value) }}
                    >
                        <option value="">Select book in the library</option>
                        {books.map(b => (
                            <option key={b.id} value={b.id}>{b.name}</option>
                        ))}
                    </select>
                </div>

                <div className='form-group' style={{ flex: 1 }}>
                    <label htmlFor="reader" className="form-label">Reader:</label>
                    <select
                        className='form-select'
                        name="reader"
                        onChange={(e) => setReader(e.target.value)}
                    >
                        <option value="">Select Reader</option>
                        {readers.map(r => (
                            <option key={r.id} value={r.id}>{r.name}</option>
                        ))}
                    </select>
                </div>
            </div>

            <table className='table table-striped table-bordered'>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Book Name</th>
                        <th>Reader Name</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        filteredJournal.map(record =>
                            <tr key={record.id}>
                                <td>{record.id}</td>
                                <td>{record.book.book.name}</td>
                                <td>{record.reader.name}</td>
                                <td>{record.startDate}</td>
                                <td>{record.endDate}</td>
                            </tr>
                        )
                    }
                </tbody>
            </table>
        </div>
    )
}

export default ListReportComponent
