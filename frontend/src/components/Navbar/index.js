import React from "react";
import { useTranslation } from "react-i18next";
import i18n from "../../translations/i18n";
import { ButtonLink } from "../ButtonLinkElement";
import {
  Nav,
  NavbarContainer,
  NavLogo,
  NavMenu,
  NavItem,
  NavLinks,
} from "./NavbarElements";
import LanguageSelectionComponent from "../LanguageSelectionComponent";

const Navbar = () => {
  const { t } = useTranslation("translation_navbar_footer");
  return (
    <Nav>
      <NavbarContainer>
        <NavLogo to="/"> SIS </NavLogo>
        <NavMenu>
          <NavItem>
            <NavLinks to="audits">{t("audits")}</NavLinks>
          </NavItem>
          <NavItem>
            <NavLinks to="users">{t("users")}</NavLinks>
          </NavItem>
          <NavItem>
            <NavLinks to="about">{t("about_service")}</NavLinks>
          </NavItem>
          <ButtonLink to="signin">{t("sign_in")}</ButtonLink>
          <ButtonLink to="signup">{t("sign_up")}</ButtonLink>
          <LanguageSelectionComponent />
        </NavMenu>
      </NavbarContainer>
    </Nav>
  );
};

export default Navbar;
