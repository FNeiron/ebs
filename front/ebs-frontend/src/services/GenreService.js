import axios from "axios";
import { REST_API_BASE_URL } from '../../config'

const SERVICE_PATH = REST_API_BASE_URL + 'genres/'

export const listGenres = () => axios.get(SERVICE_PATH + 'all');

export const createGenre = (genre) => axios.post(SERVICE_PATH + 'create', genre)

export const getGenre = (genreId) => axios.get(SERVICE_PATH + 'get/' + genreId)

export const updateGenre = (genreId, genre) => axios.put(SERVICE_PATH + 'update/' + genreId, genre)

export const deleteGenre = (genreId) => axios.delete(SERVICE_PATH + 'delete/' + genreId)