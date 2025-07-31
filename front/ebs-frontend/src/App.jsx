import './App.css'
import AuthorComponent from './components/Author/AuthorComponent'
import FooterComponent from './components/FooterComponent'
import HeaderComponent from './components/HeaderComponent'
import ListAuthorComponent from './components/Author/ListAuthorComponent'
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import ListGenreComponent from './components/Genre/ListGenreComponent'
import GenreComponent from './components/Genre/GenreComponent'
import ListStoryComponent from './components/Story/ListStoryComponent'
import ListReaderComponent from './components/Reader/ListReaderComponent'
import StoryComponent from './components/Story/StoryComponent'
import ReaderComponent from './components/Reader/ReaderComponent'
import ListBookComponent from './components/Book/ListBookComponent'
import BookComponent from './components/Book/BookComponent'
import ListBookInLibComponent from './components/BookInLib/ListBookInLibComponent'
import BookInLibComponent from './components/BookInLib/BookInLibComponent'
import DebtBookComponent from './components/BookInLib/DebtBookComponent'
import ListReportComponent from './components/Report/ListReportComponent'

function App() {

  return (
    <>
    <div id="page-container">
    <BrowserRouter>
      <HeaderComponent />
      <div id="content-wrap">
        <Routes>
          {/* // http:/localhost:3000 */ }
            <Route path='/' element = { <ListAuthorComponent /> }></Route>
            <Route path='/authors' element = { <ListAuthorComponent /> }></Route>
            <Route path='/add-author' element = { <AuthorComponent /> }></Route>
            <Route path='/edit-author/:id' element = { <AuthorComponent /> }></Route>

            <Route path='/genres' element = { <ListGenreComponent /> }></Route>
            <Route path='/add-genre' element = { <GenreComponent /> }></Route>
            <Route path='/edit-genre/:id' element = { <GenreComponent /> }></Route>

            <Route path='/stories' element = { <ListStoryComponent /> }></Route>
            <Route path='/add-story' element = { <StoryComponent /> }></Route>
            <Route path='/edit-story/:id' element = { <StoryComponent /> }></Route>

            <Route path='/readers' element = { <ListReaderComponent /> }></Route>
            <Route path='/add-reader' element = { <ReaderComponent /> }></Route>
            <Route path='/edit-reader/:id' element = { <ReaderComponent /> }></Route>

            <Route path='/books' element = { <ListBookComponent /> }></Route>
            <Route path='/add-book' element = { <BookComponent /> }></Route>
            <Route path='/edit-book/:id' element = { <BookComponent /> }></Route>

            <Route path='/books-in-lib' element = { <ListBookInLibComponent /> }></Route>
            <Route path='/add-book-in-lib' element = { <BookInLibComponent /> }></Route>
            <Route path='/edit-book-in-lib/:id' element = { <BookInLibComponent /> }></Route>
            <Route path='/debt-book/:id' element = { <DebtBookComponent /> }></Route>

            <Route path='/report' element = { <ListReportComponent /> }></Route>
        </Routes>
        </div>
      <FooterComponent />
    </BrowserRouter>
    </div>
    </>
  )
}

export default App
