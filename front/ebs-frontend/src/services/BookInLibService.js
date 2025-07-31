import axios from "axios";
import { REST_API_BASE_URL } from '../../config'

const SERVICE_PATH = REST_API_BASE_URL + 'books_in_lib/'

export const listBooksInLib = () => axios.get(SERVICE_PATH + 'all');

export const createBookInLib = (bookInLib) => axios.post(SERVICE_PATH + 'create', bookInLib)

export const getBookInLib = (bookInLibId) => axios.get(SERVICE_PATH + 'get/' + bookInLibId)

export const updateBookInLib = (bookInLibId, bookInLib) => axios.put(SERVICE_PATH + 'update/' + bookInLibId, bookInLib)

export const deleteBookInLib = (bookInLibId) => axios.delete(SERVICE_PATH + 'delete/' + bookInLibId)