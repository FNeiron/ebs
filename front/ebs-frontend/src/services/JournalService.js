import axios from "axios";
import { REST_API_BASE_URL } from '../../config'

const SERVICE_PATH = REST_API_BASE_URL + 'journal/'

export const listJournal = () => axios.get(SERVICE_PATH + 'all')

export const listOpenedJournal = () => axios.get(SERVICE_PATH + 'all-opened')

export const createJournal = (journal) => axios.post(SERVICE_PATH + 'create', journal)

export const getJournal = (journalId) => axios.get(SERVICE_PATH + 'get/' + journalId)

export const updateJournal = (journalId, journal) => axios.put(SERVICE_PATH + 'update/' + journalId, journal)

export const returnBook = (journalId) => axios.put(SERVICE_PATH + 'return-book/' + journalId)

export const debtBook = (bookInLibId, readerId) => axios.post(SERVICE_PATH + 'debt-book/' + bookInLibId + '/' + readerId)

export const deleteJournal = (journalId) => axios.delete(SERVICE_PATH + 'delete/' + journalId)