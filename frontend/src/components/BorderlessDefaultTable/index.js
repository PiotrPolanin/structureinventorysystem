import React from "react";
import { BorderlessDefaultTableStyle } from "./BorderlessDefaultTableComponents";

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
          {data.length > 0 ? (
            data.map((object, index) => (
              <tr key={index}>
                {Object.entries(object).map(([k, v]) => (
                  <td key={k}>{v}</td>
                ))}
              </tr>
            ))
          ) : (
            <tr>
              <td colSpan={columnNames.length}>No content</td>
            </tr>
          )}
        </tbody>
      </BorderlessDefaultTableStyle>
    </>
  );
};

export default BorderlessDefaultTable;
