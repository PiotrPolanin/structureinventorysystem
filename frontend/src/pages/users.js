import React from "react";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import i18n from "../translations/i18n";
import RestApiService from "../services/RestApiService";
import MainContentSection from "../components/MainContentSection";
import { AddButton } from "../components/ButtonElement";
import UsersTable from "../components/UsersTable";

const UsersPage = () => {
  const { t, i18n } = useTranslation("translation_button");
  const navigate = useNavigate();

  const redirectToUserForm = (id) => {
    navigate("/users/form/" + id, { state: { id: id } });
  };

  const [users, setUsers] = useState([]);
  const service = new RestApiService("user");

  const getUsers = () => {
    service.getAll().then((response) => {
      setUsers(response.data);
    });
  };

  useEffect(() => {
    getUsers();
  }, [users.length]);

  const deleteUser = (id) => {
    service.deleteById(id).then((response) => {
      setUsers(users.filter((user) => user.id !== id));
    });
  };

  return (
    <MainContentSection
      elements={
        <>
          <AddButton onClick={() => redirectToUserForm("new")}>
            {t("add")}
          </AddButton>
          <UsersTable
            users={users}
            deleteUser={deleteUser}
            redirectToUserForm={redirectToUserForm}
          />
        </>
      }
    />
  );
};

export default UsersPage;
