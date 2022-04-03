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

  &:hover {
    transition: all 0.2 s ease-in-out;
    background: ${({ primary }) => (primary ? "#fff" : "#01BF71")};
  }
`;

export const AddButton = styled(Button)`
  background: blue;
`;
