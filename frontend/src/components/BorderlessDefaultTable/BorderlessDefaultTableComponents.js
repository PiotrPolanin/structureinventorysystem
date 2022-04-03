import styled from "styled-components";

export const BorderlessDefaultTableStyle = styled.table`
  border: none;
  border-collapse: collapse;
  // border-collapse: separate;
  border-spacing: 10px 10px;
  vertical-align: middle;

  tbody {
    vertical-align: top;
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
      background-color: grey;
    }
  }
  thead > tr {
    background-color: #c2c2c2;
  }
`;
