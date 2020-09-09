import PropTypes from "prop-types";
import { cellShape, posShape } from "./shapes";
import React from "react";
import { Cell } from "./Cell";
import { cellHasFlag } from "./flagUtils";

export const Grid = ({
  gridData,
  onCellClick,
  onCellRightClick,
  flags,
  gridDisabled,
}) => {
  const renderRow = (row, x) => {
    return row.map((cell, y) => {
      return (
        <td key={x * row.length + y}>
          <Cell
            onClick={() => onCellClick(x, y)}
            onCellRightClick={(e) => onCellRightClick(e, x, y)}
            value={cell}
            hasFlag={cellHasFlag(x, y, flags)}
            cellsDisabled={gridDisabled}
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
  flags: PropTypes.arrayOf(posShape),
  gridDisabled: PropTypes.bool,
};
