import React, { useState } from "react";
import PropTypes from "prop-types";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";

export const SizeForm = ({ onConfirm }) => {
  const [rows, setRows] = useState(10);
  const [cols, setCols] = useState(15);
  const [mines, setMines] = useState(5);

  return (
    <div>
      <TextField
        type="number"
        id="rows"
        label="Rows"
        variant="filled"
        value={rows}
        onChange={(e) => setRows(e.target.value)}
      />
      <TextField
        type="number"
        id="cols"
        label="Columns"
        variant="filled"
        value={cols}
        onChange={(e) => setCols(e.target.value)}
      />
      <TextField
        type="number"
        id="mines"
        label="Mines"
        variant="filled"
        value={mines}
        onChange={(e) => setMines(e.target.value)}
      />
      <Button onClick={() => onConfirm(rows, cols, mines)}>
        Create New Game
      </Button>
    </div>
  );
};

SizeForm.propTypes = {
  onConfirm: PropTypes.func,
};
