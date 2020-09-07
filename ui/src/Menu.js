import React, { useState } from "react";
import PropTypes from "prop-types";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import FormDialog from "./FormDialog";

export const Menu = ({ onNewGame, onSaveGame, showSave }) => {
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
      <Button
        variant="outlined"
        color="primary"
        onClick={() => onNewGame(rows, cols, mines)}
      >
        Create New Game
      </Button>
      {showSave && <FormDialog onConfirm={onSaveGame}>Save Game</FormDialog>}
    </div>
  );
};

Menu.propTypes = {
  onNewGame: PropTypes.func,
  onSaveGame: PropTypes.func,
  showSave: PropTypes.bool,
};
