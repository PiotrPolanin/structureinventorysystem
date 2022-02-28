import React from "react";
import { BorderlessDefaultTableStyle } from "./BorderlessDefaultTableComponents";
import { TableButton } from "../TableButton";

const BorderlessDefaultTable = ({ columnNames, data }) => {
  return (
    <>
      <BorderlessDefaultTableStyle>
        <thead>
          <tr>
            {columnNames.map((cname, index) => (
              <th key={index}>{cname}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data.map((object, index) => (
            <tr key={index}>
              {Object.entries(object).map(([k, v]) => (
                <td key={k}>{v}</td>
              ))}
              <TableButton primary="true" to="/">
                edit
              </TableButton>
              <TableButton to="/">remove</TableButton>
            </tr>
          ))}
        </tbody>
      </BorderlessDefaultTableStyle>
    </>
  );
};

export default BorderlessDefaultTable;
