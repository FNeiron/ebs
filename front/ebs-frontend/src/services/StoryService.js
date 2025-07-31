import axios from "axios";
import { REST_API_BASE_URL } from '../../config'

const SERVICE_PATH = REST_API_BASE_URL + 'stories/'

export const listStories = () => axios.get(SERVICE_PATH + 'all');

export const createStory = (story) => axios.post(SERVICE_PATH + 'create', story)

export const getStory = (storyId) => axios.get(SERVICE_PATH + 'get/' + storyId)

export const updateStory = (storyId, story) => axios.put(SERVICE_PATH + 'update/' + storyId, story)

export const deleteStory = (storyId) => axios.delete(SERVICE_PATH + 'delete/' + storyId)