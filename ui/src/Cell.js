import PropTypes from "prop-types";
import cellShape from "./shapes";
import React from "react";
import Button from "@material-ui/core/Button";
import FlagOutlinedIcon from "@material-ui/icons/FlagOutlined";
import Brightness6OutlinedIcon from "@material-ui/icons/Brightness6Outlined";

export const Cell = ({ value, onClick, onCellRightClick, hasFlag }) => {
  const label = () => {
    if (value.isOpen) {
      return value.isMine ? (
        <Brightness6OutlinedIcon color="error" fontSize="small" />
      ) : value.n === 0 ? (
        " "
      ) : (
        value.n
      );
    } else {
      return hasFlag ? (
        <FlagOutlinedIcon color="error" fontSize="small" />
      ) : (
        " "
      );
    }
  };

  const isDisabled = value.isOpen;
  const colorValue = () => {
    if (!value.isOpen) return "black";
    else if (hasFlag) return "red";
    else {
      switch (value.n) {
        case 1:
          return "blue";
        case 2:
          return "green";
        case 3:
          return "red";
        case 4:
          return "navy";
        case 5:
          return "maroon";
        case 6:
          return "teal";
        case 7:
          return "black";
        default:
          return "silver";
      }
    }
  };

  return (
    <Button
      style={{
        maxWidth: "30px",
        maxHeight: "30px",
        minWidth: "30px",
        minHeight: "30px",
        color: colorValue(),
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
