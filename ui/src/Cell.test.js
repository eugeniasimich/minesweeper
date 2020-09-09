import React from "react";
import ReactDOM from "react-dom";

import { Cell } from "./Cell";

describe("Cell", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(
      <Cell
        value={{ isMine: false, n: 0, isOpen: false }}
        onClick={() => {}}
        onCellRightClick={() => {}}
        hasFlag={false}
        cellsDisabled={false}
      />,
      div
    );
    ReactDOM.unmountComponentAtNode(div);
  });
});
