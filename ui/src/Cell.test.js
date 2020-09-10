import React from "react";

import { Cell } from "./Cell";
import { render, screen, fireEvent } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";

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
  });

  it("is disabled when it is open and it is a mine", () => {
    render(
      <Cell
        value={{ isMine: false, n: 0, isOpen: true }}
        onClick={() => {}}
        onCellRightClick={() => {}}
        hasFlag={false}
        cellsDisabled={false}
      />
    );

    expect(screen.getByRole("button")).toHaveAttribute("disabled");
  });

  it("is disabled when it is open and it is not mine", () => {
    render(
      <Cell
        value={{ isMine: false, n: 0, isOpen: true }}
        onClick={() => {}}
        onCellRightClick={() => {}}
        hasFlag={false}
        cellsDisabled={false}
      />
    );

    expect(screen.getByRole("button")).toHaveAttribute("disabled");
  });

  it("shows number of mines when open", () => {
    render(
      <Cell
        value={{ isMine: false, n: 2, isOpen: true }}
        onClick={() => {}}
        onCellRightClick={() => {}}
        hasFlag={false}
        cellsDisabled={false}
      />
    );

    expect(screen.getByRole("button")).toHaveTextContent("2");
  });

  it("does not show number of mines when closed", () => {
    render(
      <Cell
        value={{ isMine: false, n: 2, isOpen: false }}
        onClick={() => {}}
        onCellRightClick={() => {}}
        hasFlag={false}
        cellsDisabled={false}
      />
    );

    expect(screen.getByRole("button")).not.toHaveTextContent("2");
  });

  it("calls onRightClick on right click", () => {
    const onRightClick = jest.fn();
    render(
      <Cell
        value={{ isMine: false, n: 2, isOpen: false }}
        onClick={() => {}}
        onCellRightClick={onRightClick}
        hasFlag={false}
        cellsDisabled={false}
      />
    );

    const rightClick = { button: 2 };
    fireEvent.contextMenu(screen.getByRole("button"));
    expect(onRightClick).toHaveBeenCalled();
  });

  it("calls onClick when not flagged", () => {
    const onClick = jest.fn();
    render(
      <Cell
        value={{ isMine: false, n: 2, isOpen: false }}
        onClick={onClick}
        onCellRightClick={() => {}}
        hasFlag={false}
        cellsDisabled={false}
      />
    );

    const rightClick = { button: 1 };
    fireEvent.click(screen.getByRole("button"), rightClick);
    expect(onClick).toHaveBeenCalled();
  });

  it("does not call onClick when flagged", () => {
    const onClick = jest.fn();
    render(
      <Cell
        value={{ isMine: false, n: 2, isOpen: false }}
        onClick={onClick}
        onCellRightClick={() => {}}
        hasFlag={true}
        cellsDisabled={false}
      />
    );

    const rightClick = { button: 1 };
    fireEvent.click(screen.getByRole("button"), rightClick);
    expect(onClick).not.toHaveBeenCalled();
  });
});
