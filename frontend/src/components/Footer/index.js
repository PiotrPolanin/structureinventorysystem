import React, { useState, useEffect } from "react";
import { animateScroll as scroll } from "react-scroll";
import { FooterContainer, WebsiteRights } from "../Footer/FooterElements";
import { SISLogo } from "../SISLogo";

const toggleNavbar = () => {
  scroll.scrollToTop();
};

const Footer = () => {
  const [currentYear, setCurrentYear] = useState();

  const getYear = () => {
    setCurrentYear(new Date().getFullYear());
  };

  useEffect(() => {
    getYear();
  }, []);

  return (
    <FooterContainer>
      <SISLogo to="/" onClick={toggleNavbar}>
        SIS
      </SISLogo>
      <WebsiteRights>SIS @ {currentYear} All rights reserved</WebsiteRights>
    </FooterContainer>
  );
};

export default Footer;
