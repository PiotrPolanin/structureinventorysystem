import React, { Component } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import HomePage from "./pages/index";
import SigninPage from "./pages/signin";
import UsersPage from "./pages/users";
import "./App.css";

class App extends Component {
  render() {
    return (
      <Router>
        <Navbar />
        <Routes>
          <Route exact path="/" element={<HomePage id="home" />} />
          <Route exact path="/signin" element={<SigninPage id="signin" />} />
          <Route exact path="/users" element={<UsersPage id="users" />} />
        </Routes>
        <Footer />
      </Router>
    );
  }
}

export default App;
