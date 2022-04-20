import React, { useState, useEffect } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import {
  FormWrap,
  FormContent,
  Form,
  FormLabel,
  FormInput,
  ButtonContent,
  FormButton,
} from "../CustomFormElements";
import RestApiService from "../../services/RestApiService";

const UserForm = () => {
  const usersPageEndpoint = "/users";
  const navigate = useNavigate();
  const location = useLocation();
  const service = new RestApiService("user");

  const initialUserFormState = {
    id: null,
    firstName: "",
    lastName: "",
    academicDegree: "",
  };

  const [user, setUser] = useState(initialUserFormState);

  const getUser = (id) => {
    service.getById(id).then((response) => {
      let user = response.data;
      setUser(user);
    });
  };

  useEffect(() => {
    if (location.state.id !== "new") {
      getUser(location.state.id);
    }
  }, []);

  const [operationStatus, setOperationStatus] = useState(null);

  const handleResponse = (response) => {
    if (response !== null) {
      try {
        if (response.status === 200) {
          setOperationStatus("OK");
        }
      } catch (error) {
        setOperationStatus(error.response.data);
      }
    }
  };

  useEffect(
    (response) => {
      handleResponse(response);
    },
    [operationStatus]
  );

  const saveOrUpdateUser = async (user) => {
    let jsonUser = JSON.stringify(user);
    if (location.state.id !== "new") {
      let response = await service.update(user.id, jsonUser);
      handleResponse(response);
    } else {
      service.create(jsonUser);
    }
  };

  // const saveOrUpdateUser = async (user) => {
  //   let jsonUser = JSON.stringify(user);
  //   if (location.state.id !== "new") {
  //     try {
  //       let response = await service.update(user.id, jsonUser);
  //       if (response.status === 200) {
  //         setOperationStatus("OK");
  //       }
  //     } catch (error) {
  //       setOperationStatus(error.response.data);
  //     }
  //   } else {
  //     service.create(jsonUser);
  //   }
  // };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setUser({ ...user, [name]: value });
  };

  const redirectToUsersPage = () => {
    navigate(usersPageEndpoint);
  };

  return (
    <FormWrap>
      <FormContent>
        <Form
          onSubmit={(event) => {
            event.preventDefault();
            saveOrUpdateUser(user);
            if (operationStatus === "OK") {
              setUser(initialUserFormState);
              // redirectToUsersPage();
            } else {
              console.log("Error operation status: ", operationStatus);
            }
          }}
        >
          <FormLabel htmlFor="firstName">First name</FormLabel>
          <FormInput
            id="firstName"
            type="text"
            name="firstName"
            value={user.firstName}
            onChange={handleChange}
          ></FormInput>
          <FormLabel htmlFor="lastName">Last name</FormLabel>
          <FormInput
            id="lastName"
            type="text"
            name="lastName"
            value={user.lastName}
            onChange={handleChange}
          ></FormInput>
          <FormLabel
            htmlFor="academicDegree"
            name="academicDegree"
            value={user.academicDegree}
          >
            Academic degree
          </FormLabel>
          <FormInput
            id="academicDegree"
            type="text"
            name="academicDegree"
            value={user.academicDegree}
            onChange={handleChange}
          ></FormInput>
          <ButtonContent>
            <FormButton>save</FormButton>
            <FormButton onClick={() => redirectToUsersPage()}>
              cancel
            </FormButton>
          </ButtonContent>
        </Form>
      </FormContent>
    </FormWrap>
  );
};

export default UserForm;
