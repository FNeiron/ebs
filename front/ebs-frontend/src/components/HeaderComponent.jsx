import React from "react";
import { FRONT_PORT } from "../../config";
import { useNavigate } from "react-router-dom";

const HeaderComponent = () => {

    const navigator = useNavigate();

    function toAuthors() {
        navigator('/authors')
    }

    function toGenres() {
        navigator('/genres')
    }

    function toStories() {
        navigator('/stories')
    }

    function toBooks() {
        navigator('/books')
    }

    function toReaders() {
        navigator('/readers')
    }

    function toBooksInLib() {
        navigator('/books-in-lib')
    }

    function toReport() {
      navigator('/report')
  }

    return (
        <div>
                <nav className="navbar navbar-expand-lg navbar-light bg-light">
                  <div className="container-fluid">
                  <a className="navbar-brand" href={`http://localhost:${FRONT_PORT}/`}>EBS</a>
                    <div className="collapse navbar-collapse" id="navbarNav">
                      <ul className="navbar-nav">
                        <li className="nav-item">
                          <a className="nav-link" onClick={toAuthors}>Authors</a>
                        </li>
                        <li className="nav-item">
                          <a className="nav-link" onClick={toGenres}>Genres</a>
                        </li>
                        <li className="nav-item">
                          <a className="nav-link" onClick={toStories}>Stories</a>
                        </li>
                        <li className="nav-item">
                          <a className="nav-link" onClick={toBooks}>Books</a>
                        </li>
                        <li className="nav-item">
                          <a className="nav-link" onClick={toReaders}>Readers</a>
                        </li>
                        <li className="nav-item">
                          <a className="nav-link" onClick={toBooksInLib}>Books In Lib</a>
                        </li>
                        <li className="nav-item">
                          <a className="nav-link" onClick={toReport}>Report</a>
                        </li>
                      </ul>
                    </div>
                  </div>
                </nav>
        </div>
    )
}

export default HeaderComponent