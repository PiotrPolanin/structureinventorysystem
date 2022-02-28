import styled from "styled-components";
import { Link as LinkRouter } from "react-router-dom";

export const Nav = styled.nav`
  background: #fff;
  height: 80px;
  justify-content: center;
  align-items: center;
  font-size: 1rem;
  position: sticky;
  top: 0;
  z-index: 0;

  @media screen and (max-width: 960px) {
    transition: 0.8 all ease;
  }
`;

export const NavbarContainer = styled.div`
  background: #2f4f4f;
  display: flex;
  justify-content: space-between;
  height: 80px;
  z-index: 1;
  width: 100%;
  padding: 0, 24px;
  // max-width: 1100px;
`;

export const NavLogo = styled(LinkRouter)`
    margin-top: 30px;
    color: #fff;
    justify-self: flex-start;
    cursor: pointer;
    font-size: 1.5rem,
    display: flex;
    align-items: center;
    margin-left: 24px;
    font-weight: bold;
`;

export const MobileIcon = styled.div`
  display: none;
  @media screen and (max-width: 768px) {
    display: block;
    position: absolute;
    top: 0;
    right: 0;
    transform: translate(-100%, 60%);
    font-size: 1.5rem;
    cursor: pointer;
    color: #fff;
  }
`;

export const NavMenu = styled.ul`
  display: flex;
  align-items: center;
  list-style: none;
  text-align: center;
  margin-right: 24px;
`;

export const NavItem = styled.li`
  height: 80px;
`;

export const NavLinks = styled(LinkRouter)`
  color: #fff;
  display: flex;
  align-items: center;
  text-decoration: none;
  padding: 0 1rem;
  height: 100%;
  cursor: pointer;
  &:active {
    border-bottom: 3px solid #00bfff;
  }
`;
