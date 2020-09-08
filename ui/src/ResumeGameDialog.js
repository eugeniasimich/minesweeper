import React, { useState, Fragment } from "react";
import PropTypes from "prop-types";
import Button from "@material-ui/core/Button";
import List from "@material-ui/core/List";
import ListItem from "@material-ui/core/ListItem";
import ListItemText from "@material-ui/core/ListItemText";
import DialogTitle from "@material-ui/core/DialogTitle";
import Dialog from "@material-ui/core/Dialog";
import Client from "./Client";
export const ResumeGameDialog = ({ onResumeGameSelection }) => {
  const [open, setOpen] = useState(false);
  const [options, setOptions] = useState([]);

  const handleClickOpen = () => {
    setOpen(true);
    Client.getSavedGamesList(setOptions);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleListItemClick = (value) => {
    setOpen(false);
    Client.getSavedGame(value, onResumeGameSelection);
  };

  return (
    <Fragment>
      <Button variant="outlined" color="primary" onClick={handleClickOpen}>
        Resume Game
      </Button>
      <Dialog onClose={handleClose} open={open}>
        <DialogTitle>Choose a game to resume</DialogTitle>
        <List>
          {options.map((name) => (
            <ListItem
              button
              onClick={() => handleListItemClick(name)}
              key={name}
            >
              <ListItemText primary={name} />
            </ListItem>
          ))}
        </List>
      </Dialog>
    </Fragment>
  );
};

ResumeGameDialog.propTypes = {
  onResumeGameSelection: PropTypes.func,
};

export default ResumeGameDialog;
