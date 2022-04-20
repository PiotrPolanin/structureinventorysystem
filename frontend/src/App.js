import React, { Component } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import HomePage from "./pages/index";
import AuditsPage from "./pages/audits";
import AboutPage from "./pages/about";
import SigninPage from "./pages/signin";
import SignupPage from "./pages/signup";
import UsersPage from "./pages/users";
import UserFormPage from "./pages/user_form";
import "./App.css";

class App extends Component {
  render() {
    return (
      <Router>
        <Navbar />
        <Routes>
          <Route exact path="/" element={<HomePage />} />
          <Route exact path="/audits" element={<AuditsPage />} />
          <Route exact path="/signin" element={<SigninPage />} />
          <Route exact path="/signup" element={<SignupPage />} />
          <Route exact path="/users" element={<UsersPage />} />
          <Route exact path="/users/form/:id" element={<UserFormPage />} />
          <Route exact path="/about" element={<AboutPage />} />
        </Routes>
        <Footer />
      </Router>
    );
  }
}

export default App;
