import React from "react";
import { useState, useEffect } from "react";
import BorderlessDefaultTable from "../BorderlessDefaultTable";
import UsersTable from "../UsersTable";
import RestApiService from "../../services/RestApiService";

const UserListComponent = (props) => {
  const [users, setUsers] = useState([]);
  const service = new RestApiService("user");

  const getUsers = () => {
    service.getAll().then((response) => {
      setUsers(response.data);
    });
  };

  useEffect(() => {
    getUsers();
  }, []);

  const deleteUser = (id) => {
    service.deleteById(id).then((response) => {
      setUsers(users.filter((user) => user.id !== id));
    });
  };

  const updateUser = (id) => {
      
  }

  return (
    <>
      <UsersTable
        users={users}
        deleteUser={deleteUser}
        updateUser={updateUser}
      />
    </>
  );
};

export default UserListComponent;
