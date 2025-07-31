import React, {useEffect, useState} from 'react'
import {deleteStory, listStories} from '../../services/StoryService'
import { useNavigate } from 'react-router-dom'

const ListStoryComponent = () => {

    const [stories, setStories] = useState([])

    const navigator = useNavigate();

    useEffect(() => {
        getAllStories();
    }, [])

    function getAllStories() {
        listStories().then((response) => {
            setStories(response.data);
        }).catch(error => {
            console.error(error);
        })
    }


    function addNewStory() {
        navigator('/add-story')
    }

    function updateStory(id) {
        navigator(`/edit-story/${id}`)
    }

    function removeStory(id) {

        if(window.confirm("Are you sure you want to delete this story?")) {
         console.log(id)

         deleteStory(id).then((response) => {
            getAllStories()
         }).catch(error => {
            console.error(error);
         })
    }
}

  return (
    <div className='container'>

        <h2 className='text-center'>List of Stories</h2>
        <button className='btn btn-primary mb-2' onClick= { addNewStory }>Add Story</button>
        <table className='table table-striped table-bordered'>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Genre</th>
                    <th className='action-column'>Actions</th>
                </tr>
            </thead>
            <tbody>
                {
                    stories.map(story =>
                        <tr key={story.id}>
                            <td>{story.id}</td>
                            <td>{story.name}</td>
                            <td>{story.genre.name}</td>
                            <td>
                                <button className='btn btn-info btn-sm' onClick={() => updateStory(story.id)}>Update</button>
                                <button className='btn btn-danger btn-sm' onClick={() => removeStory(story.id)}
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

export default ListStoryComponent