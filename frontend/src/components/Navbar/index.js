import React from "react";
import { ButtonLink } from "../ButtonLinkElement";
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
            <ButtonLink to="signin">Sign in</ButtonLink>
            <ButtonLink to="signup">Sign up</ButtonLink>
          </NavMenu>
        </NavbarContainer>
      </Nav>
    </>
  );
};

export default Navbar;
