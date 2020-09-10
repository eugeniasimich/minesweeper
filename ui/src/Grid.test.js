import React from "react";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
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
