import React from "react";
import ReactDOM from "react-dom";

import { Grid } from "./Grid";

describe("App tests", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(
      <Grid
        gridData={[]}
        onCellClick={() => {}}
        onCellRightClick={() => {}}
        flags={[]}
        gridDisabled={false}
      />,
      div
    );
    ReactDOM.unmountComponentAtNode(div);
  });
});
