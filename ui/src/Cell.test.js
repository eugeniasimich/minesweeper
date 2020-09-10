import React from "react";

import { Cell } from "./Cell";
import { render } from "@testing-library/react";
//import "@testing-library/jest-dom/extend-expect";

describe("Cell", () => {
  it("renders without crashing", () => {
    render(
      <Cell
        value={{ isMine: false, n: 0, isOpen: false }}
        onClick={() => {}}
        onCellRightClick={() => {}}
        hasFlag={false}
        cellsDisabled={false}
      />
    );
  });
  /* 
  it("is disabled when grid is", () => {
    render(
      <Cell
        value={{ isMine: false, n: 0, isOpen: false }}
        onClick={() => {}}
        onCellRightClick={() => {}}
        hasFlag={false}
        cellsDisabled={true}
      />
    );

    expect(screen.getByRole("button")).toHaveAttribute("disabled");
  }); */
});
