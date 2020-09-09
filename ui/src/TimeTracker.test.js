import React from "react";
import ReactDOM from "react-dom";

import { TimeTracker } from "./TimeTracker";

describe("TimeTracker", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(
      <TimeTracker seconds={2} setSeconds={() => {}} finished={false} />,
      div
    );
    ReactDOM.unmountComponentAtNode(div);
  });
});
