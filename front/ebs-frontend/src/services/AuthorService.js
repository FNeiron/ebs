import axios from "axios";
import { REST_API_BASE_URL } from '../../config'

const SERVICE_PATH = REST_API_BASE_URL + 'authors/'

export const listAuthors = () => axios.get(SERVICE_PATH + 'all');

export const createAuthor = (author) => axios.post(SERVICE_PATH + 'create', author)

export const getAuthor = (authorId) => axios.get(SERVICE_PATH + 'get/' + authorId)

export const updateAuthor = (authorId, author) => axios.put(SERVICE_PATH + 'update/' + authorId, author)

export const deleteAuthor = (authorId) => axios.delete(SERVICE_PATH + 'delete/' + authorId)