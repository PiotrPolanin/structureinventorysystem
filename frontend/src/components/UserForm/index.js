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
import FormMessage from "../FormMessageComponent";

const UserForm = () => {
  const usersPageEndpoint = "/users";
  const navigate = useNavigate();
  const location = useLocation();
  const service = new RestApiService("user");
  let operationStatus = null;

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

  const [message, setMessage] = useState(null);

  useEffect(() => {}, [message]);

  const successOperationHandler = (response) => {
    if (response.status === 200 || response.status === 201) {
      operationStatus = "OK";
    }
  };

  const failureOperationHandler = (error) => {
    operationStatus = error.response.data;
  };

  const saveOrUpdateUser = async (user) => {
    let jsonUser = JSON.stringify(user);
    if (location.state.id !== "new") {
      try {
        let response = await service.update(user.id, jsonUser);
        successOperationHandler(response);
      } catch (error) {
        failureOperationHandler(error);
      }
    } else {
      try {
        let response = await service.create(jsonUser);
        successOperationHandler(response);
      } catch (error) {
        failureOperationHandler(error);
      }
    }
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    setUser({ ...user, [name]: value });
  };

  const redirectToUsersPage = () => {
    navigate(usersPageEndpoint);
  };

  const handleUserForm = async (user) => {
    await saveOrUpdateUser(user);
    if (operationStatus === "OK") {
      setUser(initialUserFormState);
      setMessage(null);
      redirectToUsersPage();
    } else {
      setMessage(operationStatus);
    }
  };

  return (
    <FormWrap>
      <FormContent>
        <Form
          onSubmit={(event) => {
            event.preventDefault();
            handleUserForm(user);
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
          {message !== null ? (
            <FormMessage message={message} isError={true} />
          ) : null}
        </Form>
      </FormContent>
    </FormWrap>
  );
};

export default UserForm;
