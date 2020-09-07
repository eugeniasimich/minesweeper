import React, { useState } from "react";
import PropTypes from "prop-types";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import SaveGameDialog from "./SaveGameDialog";

export const Menu = ({ onNewGame, onSaveGame, showSave, onResumeGame }) => {
  const [rows, setRows] = useState(10);
  const [cols, setCols] = useState(15);
  const [mines, setMines] = useState(10);

  return (
    <div>
      <TextField
        type="number"
        label="Rows"
        value={rows}
        onChange={(e) => setRows(e.target.value)}
      />
      <TextField
        type="number"
        label="Columns"
        value={cols}
        onChange={(e) => setCols(e.target.value)}
      />
      <TextField
        type="number"
        label="Mines"
        value={mines}
        onChange={(e) =>
          setMines(Math.max(1, Math.min(e.target.value, rows * cols - 1)))
        }
        InputProps={{
          inputProps: {
            min: 1,
            max: rows * cols - 1,
          },
        }}
      />
      <Button
        variant="outlined"
        color="primary"
        onClick={() => onNewGame(rows, cols, mines)}
      >
        Create New Game
      </Button>
      {showSave && (
        <SaveGameDialog onConfirm={onSaveGame}>Save Game</SaveGameDialog>
      )}
      <Button variant="outlined" color="primary" onClick={onResumeGame}>
        Resume Game
      </Button>
    </div>
  );
};

Menu.propTypes = {
  onNewGame: PropTypes.func,
  onSaveGame: PropTypes.func,
  showSave: PropTypes.bool,
  onResumeGame: PropTypes.func,
};
