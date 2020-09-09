import React, { useState } from "react";
import PropTypes from "prop-types";
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import SaveGameDialog from "./SaveGameDialog";
import ResumeGameDialog from "./ResumeGameDialog";
import Auth from "./Auth";
import { useHistory } from "react-router-dom";
import SessionCookie from "./SessionCookie";
import { makeStyles } from "@material-ui/core/styles";
const useStyles = makeStyles((theme) => ({
  items: {
    "& > *": {
      margin: theme.spacing(1),
    },
  },
}));

export const Menu = ({
  isAuthMode,
  onNewGame,
  onSaveGame,
  showSave,
  getResumeGameOptions,
  onResumeGameSelection,
}) => {
  const [rows, setRows] = useState(10);
  const [cols, setCols] = useState(15);
  const [mines, setMines] = useState(10);
  const history = useHistory();
  const classes = useStyles();

  const forceLimits = (min, max, val) => {
    return Math.max(min, Math.min(val, max));
  };

  const getUsername = () => {
    return SessionCookie.get().split("-")[0];
  };
  return (
    <div className={classes.items}>
      <TextField
        type="number"
        label="Rows"
        value={rows}
        onChange={(e) => setRows(forceLimits(2, 30, e.target.value))}
      />
      <TextField
        type="number"
        label="Columns"
        value={cols}
        onChange={(e) => setCols(forceLimits(2, 20, e.target.value))}
      />
      <TextField
        type="number"
        label="Mines"
        value={mines}
        onChange={(e) =>
          setMines(forceLimits(1, rows * cols - 1, e.target.value))
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
      <Button
        variant="outlined"
        color="primary"
        onClick={() => {
          onResumeGameSelection({ g: undefined, seconds: 0, flags: [] });
          Auth.signout();
          let { from } = { from: { pathname: "/" } };
          return history.replace(from);
        }}
      >
        {!isAuthMode
          ? "Log in"
          : getUsername()
          ? "Sign out, " + getUsername()
          : "Sign out"}
      </Button>
    </div>
  );
};

Menu.propTypes = {
  isAuthMode: PropTypes.bool,
  onNewGame: PropTypes.func,
  onSaveGame: PropTypes.func,
  showSave: PropTypes.bool,
  onResumeGameSelection: PropTypes.func,
};
