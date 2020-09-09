import React, { useState } from "react";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import PropTypes from "prop-types";
import Grid from "@material-ui/core/Grid";
import { makeStyles } from "@material-ui/core/styles";
import { Link, useHistory } from "react-router-dom";
import Auth from "./Auth";

const useStyles = makeStyles((theme) => ({
  buttons: {
    "& > *": {
      margin: theme.spacing(1),
    },
  },
  items: {
    padding: theme.spacing(2),
    textAlign: "center",
    color: theme.palette.text.secondary,
  },
  error: {
    color: "red",
  },
}));

const Login = ({ csrfToken }) => {
  const classes = useStyles();
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [errorMess, setErrorMess] = useState();
  const history = useHistory();
  const onFinish = (session) => {
    let { from } = { from: { pathname: "/" } };
    history.replace(from);
  };
  const handleLogin = () => {
    return (
      ensureNotEmpty() &&
      Auth.login(username, password, csrfToken, setErrorMess, onFinish)
    );
  };
  const handleSignup = () => {
    return (
      ensureNotEmpty() &&
      Auth.signup(username, password, csrfToken, setErrorMess, onFinish)
    );
  };

  const ensureNotEmpty = () => {
    if (!username || !password)
      setErrorMess("Username and Password must not be empty");
    return username && password;
  };

  return (
    <div className="Login">
      <Grid container spacing={1}>
        <Grid item xs={12} className={classes.items}>
          <h1>Login or Signup</h1>
        </Grid>
        <Grid item xs={12} className={classes.items}>
          <TextField
            variant="outlined"
            label="Username"
            onChange={(e) => setUsername(e.target.value)}
          />
        </Grid>
        <Grid item xs={12} className={classes.items}>
          <div>
            <TextField
              variant="outlined"
              type="password"
              label="Password"
              onChange={(e) => setPassword(e.target.value)}
            />
            {errorMess && <p className={classes.error}>{errorMess}</p>}
          </div>
        </Grid>
        <Grid item xs={12} className={classes.items}>
          <div className={classes.buttons}>
            <Button
              variant="outlined"
              color="primary"
              onClick={(event) => handleLogin(event)}
            >
              Login
            </Button>
            <Button
              variant="outlined"
              color="primary"
              onClick={(event) => handleSignup(event)}
            >
              Signup
            </Button>
          </div>
        </Grid>
        <Grid item xs={12} className={classes.items}>
          <div className={classes.buttons}>
            <Button
              variant="outlined"
              color="primary"
              component={Link}
              to={"/public"}
            >
              Play incognito
            </Button>
          </div>
        </Grid>
      </Grid>
    </div>
  );
};

Login.propTypes = {
  csrfToken: PropTypes.string,
};

export default Login;
