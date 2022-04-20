import styled from "styled-components";

export const BorderlessDefaultTableStyle = styled.table`
  border: none;
  border-collapse: collapse;
  border-spacing: 10px 10px;

  tbody {
    text-align: center;
  }
  td,
  th {
    border: none;
  }
  td {
    padding: 5px 10px;
  }

  tbody tr {
    :nth-of-type(odd) {
      background-color: #efefef;
    }
    :hover {
      background-color: #dcdcdc;
    }
  }
  thead > tr {
    background-color: #c2c2c2;
  }

  td[colspan]:not([colspan="1"]) {
    text-align: center;
  }
`;
