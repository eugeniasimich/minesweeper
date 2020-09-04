import PropTypes from "prop-types";
import cellShape from "./shapes";
import React from "react";
import Button from "@material-ui/core/Button";

export const Cell = ({ value, onClick, onCellRightClick }) => {
  const label = () => {
    if (value.isOpen) {
      return value.isMine ? "*" : value.n === 0 ? " " : value.n;
    } else {
      return value.isFlag ? "F" : " ";
    }
  };

  const isDisabled = value.isOpen || value.isFlag;

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
      onClick={onClick}
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
};
