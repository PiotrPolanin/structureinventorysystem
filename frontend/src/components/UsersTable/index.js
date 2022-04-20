import React from "react";
import { Button } from "../ButtonElement";
import { BorderlessDefaultTableStyle } from "../BorderlessDefaultTable/BorderlessDefaultTableComponents";

const UserTable = (props) => {
  return (
    <BorderlessDefaultTableStyle>
      <thead>
        <tr>
          <th>first name</th>
          <th>last name</th>
          <th>academic degree</th>
          <th>action</th>
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
                  edit
                </Button>
                <Button onClick={() => props.deleteUser(user.id)}>
                  remove
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
