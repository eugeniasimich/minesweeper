import React from "react";
import { render } from "@testing-library/react";
import { Grid } from "./Grid";

describe("Grid", () => {
  it("renders without crashing", () => {
    render(
      <Grid
        gridData={[]}
        onCellClick={() => {}}
        onCellRightClick={() => {}}
        flags={[]}
        gridDisabled={false}
      />
    );
  });
});
