import React, { useState, useEffect } from "react";
import { animateScroll as scroll } from "react-scroll";
import { useTranslation } from "react-i18next";
import i18n from "../../translations/i18n";
import { FooterContainer, WebsiteRights } from "../Footer/FooterElements";
import { SISLogo } from "../SISLogo";

const toggleNavbar = () => {
  scroll.scrollToTop();
};

const Footer = () => {
  const [currentYear, setCurrentYear] = useState();
  const { t } = useTranslation("translation_navbar_footer");

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
      <WebsiteRights>
        SIS @ {currentYear} {t("reserved_rights")}
      </WebsiteRights>
    </FooterContainer>
  );
};

export default Footer;
