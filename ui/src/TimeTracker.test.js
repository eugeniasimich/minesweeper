import React from "react";
import { render } from "@testing-library/react";
import { TimeTracker } from "./TimeTracker";

describe("TimeTracker", () => {
  it("renders without crashing", () => {
    render(<TimeTracker seconds={2} setSeconds={() => {}} finished={false} />);
  });
});
