import React, { useEffect, useState } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import { createAuthor, getAuthor, updateAuthor } from '../../services/AuthorService'

const AuthorComponent = () => {

    const [name, setName] = useState('')
    const [nickname, setNickname] = useState('')
    const [birthday, setBirthday] = useState('')

    const [errors, setErrors] = useState({
        name: '',
        nickname: '',
        birthday: ''
    })

    const {id} = useParams()
    const navigator = useNavigate()

    useEffect(() => {
        if(id) {
            getAuthor(id).then((response) => {
                setName(response.data.name);
                setNickname(response.data.nickname);
                setBirthday(response.data.birthday);
            }).catch(error => {
                console.error(error);
            })
        }
    }, [])

    function saveOrUpdateAuthor(e) {
        e.preventDefault();

        if(validateForm()) {

            const author = {name, nickname, birthday}
            console.log(author)


            if(id) {
                updateAuthor(id, author).then((response) => {
                    console.log(response.data);
                    navigator('/authors')
                }).catch(error => {
                    console.error(error);
                })
            } else {
                createAuthor(author).then((response) => {
                    console.log(response.data)
                    navigator('/authors')
                }).catch(error => {
                    console.log(error);
                })
            }
        }
    }

    function cancelForm(e) {
        e.preventDefault();
        navigator('/authors')
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

        if(nickname.trim()) {
            errorsCopy.nickname = '';
        } else {
            errorsCopy.nickname = 'Nickname is required';
            valid = false;
        }

        if(birthday.trim()) {
            errorsCopy.birthday = '';
        } else {
            errorsCopy.birthday = 'Birthday is required';
            valid = false;
        }

        setErrors(errorsCopy);

        return valid;
    }

    function pageTitle() {
        if(id) {
            return <h2 className='text-center'>Update Author</h2>
        } else {
            return <h2 className='text-center'>Add Author</h2>
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
                            placeholder='Enter Author Name'
                            name='name'
                            value={name}
                            className={`form-control ${errors.name ? 'is-invalid': ''}`}
                            onChange={(e) => setName(e.target.value)}
                            >
                            </input>
                            {errors.name && <div className='invalid-feedback'>{errors.name}</div>}
                        </div>

                        <div className='form-group mb-2'>
                            <label htmlFor="form-label">Nickname:</label>
                            <input
                            type="text"
                            placeholder='Enter Author Nickname'
                            name='nickname'
                            value={nickname}
                            className={`form-control ${errors.nickname ? 'is-invalid': ''}`}
                            onChange={(e) => setNickname(e.target.value)}
                            >
                            </input>
                            {errors.nickname && <div className='invalid-feedback'>{errors.nickname}</div>}
                        </div>

                        <div className='form-group mb-2'>
                            <label htmlFor="form-label">Birthday:</label>
                            <input
                            type="date"
                            placeholder='Enter Author Birthday'
                            name='birthday'
                            value={birthday}
                            className={`form-control ${errors.birthday ? 'is-invalid': ''}`}
                            onChange={(e) => setBirthday(e.target.value)}
                            >
                            </input>
                            {errors.birthday && <div className='invalid-feedback'>{errors.birthday}</div>}
                        </div>

                        <button className='btn btn-success' onClick={saveOrUpdateAuthor} >Submit</button>
                        <button className='btn btn-danger' onClick={cancelForm} >Cancel</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
  )
}

export default AuthorComponent