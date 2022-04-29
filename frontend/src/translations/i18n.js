import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import LanguageDetector from "i18next-browser-languagedetector";

import Translations_navbar_footer_en from "./en/translations_navbar_footer_en.js";
import Translations_button_en from "./en/translations_button_en.js";
import Translations_user_en from "./en/translations_user_en.js";
import Translations_navbar_footer_pl from "./pl/translations_navbar_footer_pl.js";
import Translations_button_pl from "./pl/translations_button_pl.js";
import Translations_user_pl from "./pl/translations_user_pl.js";

i18n
  .use(LanguageDetector)
  .use(initReactI18next)
  .init({
    returnEmptyString: false,
    resources: {
      en: {
        translation_navbar_footer: Translations_navbar_footer_en,
        translation_button: Translations_button_en,
        translation_user: Translations_user_en,
      },
      pl: {
        translation_navbar_footer: Translations_navbar_footer_pl,
        translation_button: Translations_button_pl,
        translation_user: Translations_user_pl,
      },
    },
  });

export { i18n };
