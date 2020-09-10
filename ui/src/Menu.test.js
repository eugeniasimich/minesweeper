import React from "react";
import { render } from "@testing-library/react";
import { Menu } from "./Menu";

describe("Menu", () => {
  it("renders without crashing", () => {
    render(
      <Menu
        isAuthMode={false}
        onNewGame={() => {}}
        onSaveGame={() => {}}
        showSave={false}
        getResumeGameOptions={() => {}}
        onResumeGameSelection={() => {}}
      />
    );
  });
});
