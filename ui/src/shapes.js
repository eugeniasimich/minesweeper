import PropTypes from "prop-types";

export const cellShape = {
  isOpen: PropTypes.bool,
  isMine: PropTypes.bool,
  n: PropTypes.number,
};

export const posShape = {
  row: PropTypes.number,
  col: PropTypes.number,
};
