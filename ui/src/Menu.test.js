import React from "react";
import ReactDOM from "react-dom";

import { Menu } from "./Menu";

describe("Menu", () => {
  it("renders without crashing", () => {
    const div = document.createElement("div");
    ReactDOM.render(
      <Menu
        isAuthMode={false}
        onNewGame={() => {}}
        onSaveGame={() => {}}
        showSave={false}
        getResumeGameOptions={() => {}}
        onResumeGameSelection={() => {}}
      />,
      div
    );
    ReactDOM.unmountComponentAtNode(div);
  });
});
