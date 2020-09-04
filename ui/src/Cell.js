import PropTypes from "prop-types";
import cellShape from "./shapes";
import React from "react";
import Button from "@material-ui/core/Button";

export const Cell = ({ value, onClick, onCellRightClick, hasFlag }) => {
  const label = () => {
    if (value.isOpen) {
      return value.isMine ? "*" : value.n === 0 ? " " : value.n;
    } else {
      return hasFlag ? "F" : " ";
    }
  };

  const isDisabled = value.isOpen;

  return (
    <Button
      style={{
        maxWidth: "30px",
        maxHeight: "30px",
        minWidth: "30px",
        minHeight: "30px",
      }}
      variant="outlined"
      disabled={isDisabled}
      onClick={(e) => !hasFlag && onClick(e)}
      onContextMenu={onCellRightClick}
    >
      {label()}
    </Button>
  );
};

Cell.propTypes = {
  value: PropTypes.objectOf(PropTypes.shape(cellShape)),
  onClick: PropTypes.func,
  onCellRightClick: PropTypes.func,
  hasFlag: PropTypes.bool,
};
