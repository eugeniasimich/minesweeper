import React, { useState } from "react";
import PropTypes from "prop-types";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import SaveGameDialog from "./SaveGameDialog";
import ResumeGameDialog from "./ResumeGameDialog";
import Auth from "./Auth";
import { useHistory } from "react-router-dom";
export const Menu = ({
  isAuthMode,
  onNewGame,
  onSaveGame,
  showSave,
  getResumeGameOptions,
  onResumeGameSelection,
  username,
}) => {
  const [rows, setRows] = useState(10);
  const [cols, setCols] = useState(15);
  const [mines, setMines] = useState(10);
  const history = useHistory();
  return (
    <div>
      <TextField
        type="number"
        label="Rows"
        value={rows}
        onChange={(e) => setRows(Math.max(1, Math.min(e.target.value, 50)))}
      />
      <TextField
        type="number"
        label="Columns"
        value={cols}
        onChange={(e) => setCols(Math.max(1, Math.min(e.target.value, 50)))}
      />
      <TextField
        type="number"
        label="Mines"
        value={mines}
        onChange={(e) =>
          setMines(Math.max(0, Math.min(e.target.value, rows * cols - 1)))
        }
      />
      <Button
        variant="outlined"
        color="primary"
        onClick={() => onNewGame(rows, cols, mines)}
      >
        Create New Game
      </Button>
      {isAuthMode && showSave && (
        <SaveGameDialog onConfirm={onSaveGame}>Save Game</SaveGameDialog>
      )}
      {isAuthMode && (
        <ResumeGameDialog
          onResumeGameSelection={onResumeGameSelection}
          getResumeGameOptions={getResumeGameOptions}
        />
      )}
      {isAuthMode && (
        <Button
          variant="outlined"
          color="primary"
          onClick={() => {
            Auth.signout();
            let { from } = { from: { pathname: "/" } };
            return history.replace(from);
          }}
        >
          {"Sign Out, " + username}
        </Button>
      )}
    </div>
  );
};

Menu.propTypes = {
  isAuthMode: PropTypes.bool,
  onNewGame: PropTypes.func,
  onSaveGame: PropTypes.func,
  showSave: PropTypes.bool,
  onResumeGameSelection: PropTypes.func,
  username: PropTypes.string,
};
