import axios from "axios";
import { REST_API_BASE_URL } from '../../config'

const SERVICE_PATH = REST_API_BASE_URL + 'books/'

export const listBooks = () => axios.get(SERVICE_PATH + 'all');

export const createBook = (book) => axios.post(SERVICE_PATH + 'create', book)

export const getBook = (bookId) => axios.get(SERVICE_PATH + 'get/' + bookId)

export const updateBook = (bookId, book) => axios.put(SERVICE_PATH + 'update/' + bookId, book)

export const deleteBook = (bookId) => axios.delete(SERVICE_PATH + 'delete/' + bookId)