import axios from "axios";
import { REST_API_BASE_URL } from '../../config'

const SERVICE_PATH = REST_API_BASE_URL + 'readers/'

export const listReaders = () => axios.get(SERVICE_PATH + 'all');

export const createReader = (reader) => axios.post(SERVICE_PATH + 'create', reader)

export const getReader = (readerId) => axios.get(SERVICE_PATH + 'get/' + readerId)

export const updateReader = (readerId, reader) => axios.put(SERVICE_PATH + 'update/' + readerId, reader)

export const deleteReader = (readerId) => axios.delete(SERVICE_PATH + 'delete/' + readerId)