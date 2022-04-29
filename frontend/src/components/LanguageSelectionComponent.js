import React, { useState } from "react";
import { i18n } from "../translations/i18n";

const LanguageSelectionComponent = () => {
  const [language, setLanguage] = useState("en");

  const changeLanguage = (event) => {
    event.preventDefault();
    setLanguage(event.target.value);
    i18n.changeLanguage(event.target.value);
  };

  return (
    <select onChange={changeLanguage} value={language}>
      <option value="en">EN</option>
      <option value="pl">PL</option>
    </select>
  );
};

export default LanguageSelectionComponent;
