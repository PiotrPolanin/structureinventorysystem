import React from "react";
import { Button } from "../ButtonElement";
import {
  Nav,
  NavbarContainer,
  NavLogo,
  NavMenu,
  NavItem,
  NavLinks,
} from "./NavbarElements";

const Navbar = () => {
  return (
    <>
      <Nav>
        <NavbarContainer>
          <NavLogo to="/"> SIS </NavLogo>
          <NavMenu>
            <NavItem>
              <NavLinks to="audits">Audits</NavLinks>
            </NavItem>
            <NavItem>
              <NavLinks to="users">Users</NavLinks>
            </NavItem>
            <NavItem>
              <NavLinks to="about">About</NavLinks>
            </NavItem>
            <Button to="signin">Sign in</Button>
            <Button to="signup">Sign up</Button>
          </NavMenu>
        </NavbarContainer>
      </Nav>
    </>
  );
};

export default Navbar;
