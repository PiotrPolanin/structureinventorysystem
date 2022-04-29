import React from "react";
import { useTranslation } from "react-i18next";
import i18n from "../../translations/i18n";
import { Button } from "../ButtonElement";
import { BorderlessDefaultTableStyle } from "../BorderlessDefaultTable/BorderlessDefaultTableComponents";

const UserTable = (props) => {
  const { t, i18n } = useTranslation([
    "translation_user",
    "translation_button",
  ]);

  return (
    <BorderlessDefaultTableStyle>
      <thead>
        <tr>
          <th>{t("first_name", { ns: "translation_user" })}</th>
          <th>{t("last_name", { ns: "translation_user" })}</th>
          <th>{t("academic_degree", { ns: "translation_user" })}</th>
          <th>{t("operation", { ns: "translation_user" })}</th>
        </tr>
      </thead>
      <tbody>
        {props.users.length > 0 ? (
          props.users.map((user, index) => (
            <tr key={index}>
              <td>{user.firstName}</td>
              <td>{user.lastName}</td>
              <td>{user.academicDegree}</td>
              <td>
                <Button
                  primary="true"
                  onClick={() => props.redirectToUserForm(user.id)}
                >
                  {t("edit", { ns: "translation_button" })}
                </Button>
                <Button onClick={() => props.deleteUser(user.id)}>
                  {t("delete", { ns: "translation_button" })}
                </Button>
              </td>
            </tr>
          ))
        ) : (
          <tr>
            <td colSpan={4}>No content</td>
          </tr>
        )}
      </tbody>
    </BorderlessDefaultTableStyle>
  );
};

export default UserTable;
