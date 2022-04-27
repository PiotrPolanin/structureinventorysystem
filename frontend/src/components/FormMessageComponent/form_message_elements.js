import styled from "styled-components";

export const FormMessageParagraph = styled.p`
  margin-top: 1rem;
  margin-bottom: 1rem;
  margin-left: 0;
  margin-right: 0;
  color: ${({ isError }) => (isError ? "red" : "blue")};
`;

export const FormMessageText = styled.span`
  display: flex;
  justify-content: center;
  text-align: center;
  font-weight: bold;
  font-size: 1rem;
`;
