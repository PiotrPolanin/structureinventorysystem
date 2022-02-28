package com.company.structureinventorysystem.configuration;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class CustomLocaleResolver extends AcceptHeaderLocaleResolver {

    private static final List<Locale> LOCALES = Arrays.asList(new Locale("de"), new Locale("en"), new Locale("es"), new Locale("fr"), new Locale("pl"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        if (!StringUtils.hasText(request.getHeader("Accept-Language"))) {
            return Locale.getDefault();
        }
        List<Locale.LanguageRange> list = Locale.LanguageRange.parse(request.getHeader("Accept-Language"));
        Locale locale = Locale.lookup(list, LOCALES);
        return locale;
    }
}
