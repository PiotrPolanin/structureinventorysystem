import React from "react";
import { useState, useEffect } from "react";
import RestApiService from "../../services/RestApiService";
import BorderlessDefaultTable from "../BorderlessDefaultTable";

const UserListComponent = () => {
  const [users, setUsers] = useState([]);
  const getUsers = () => {
    var service = new RestApiService("http://localhost:8080/api/user");
    service.getAll().then((response) => {
      setUsers(response.data);
    });
  };

  const tableColumnsNames = [
    "id",
    "first name",
    "last name",
    "academic degree",
    "roles",
    "action",
  ];

  useEffect(() => {
    getUsers();
  }, []);

  return (
    <>
      <BorderlessDefaultTable columnNames={tableColumnsNames} data={users} />
    </>
  );
};

export default UserListComponent;
