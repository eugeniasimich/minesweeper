import PropTypes from "prop-types";
import cellShape from "./shapes";
import React from "react";
import { Cell } from "./Cell";

export const Grid = ({ gridData, onCellClick, onCellRightClick }) => {
  const renderRow = (row, x) => {
    return row.map((cell, y) => {
      return (
        <td key={x * row.length + y}>
          <Cell
            onClick={() => onCellClick(x, y)}
            onCellRightClick={(e) => onCellRightClick(e, x, y)}
            value={cell}
          />
        </td>
      );
    });
  };

  const renderGrid = () => {
    return gridData.map((row, x) => {
      return <tr> {renderRow(row, x)}</tr>;
    });
  };

  return <table className="grid">{renderGrid()}</table>;
};

Grid.propTypes = {
  gridData: PropTypes.arrayOf(PropTypes.arrayOf(cellShape)),
  onCellClick: PropTypes.func,
  onCellRightClick: PropTypes.func,
};
