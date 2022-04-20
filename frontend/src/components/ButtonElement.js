import styled from "styled-components";

export const Button = styled.button`
  border-radius: 30px;
  background: ${({ primary }) => (primary ? "#3cb371" : "#dc143c")};
  white-space: nowrap;
  margin-right: 5px;
  padding: ${({ big }) => (big ? "10px 20px" : "5px 10px")};
  color: ${({ dark }) => (dark ? "#010606" : "#fff")};
  font-size: ${({ bigFont }) => (bigFont ? "18px" : "12px")};
  outline: none;
  border: none;
  cursor: pointer;
  display: inline;
  justify-content: center;
  align-items: center;
  transition: all 0.2 s ease-in-out;
  width: 4rem;
  height: 2rem;
  margin-top: 10px;
  margin-bottom: 10px;

  &:hover {
    transition: all 0.2 s ease-in-out;
    outline-style: solid;
    outline-color: ${({ primary }) => (primary ? "#fff" : "#ffdab9")};
  }
`;

export const AddButton = styled(Button)`
  background: blue;
`;
