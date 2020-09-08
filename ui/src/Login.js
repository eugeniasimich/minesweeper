import React, { useState } from "react";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";
import PropTypes from "prop-types";
import Grid from "@material-ui/core/Grid";
import { makeStyles } from "@material-ui/core/styles";

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
}));

const Login = (setAuth) => {
  const classes = useStyles();
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();

  const handleLogin = () => alert(username + " " + password);
  const handleSignup = () => alert(password + " " + username);

  return (
    <div className="Login">
      <Grid container spacing={1}>
        <Grid item xs={12} className={classes.items}>
          <h1>Login</h1>
        </Grid>
        <Grid item xs={12} className={classes.items}>
          <TextField
            variant="outlined"
            label="Username"
            onChange={(e) => setUsername(e.target.value)}
          />
        </Grid>
        <Grid item xs={12} className={classes.items}>
          <TextField
            variant="outlined"
            type="password"
            label="Password"
            onChange={(e) => setPassword(e.target.value)}
          />
        </Grid>
        <Grid item xs={12} className={classes.items}>
          <div className={classes.buttons}>
            <Button
              variant="outlined"
              color="primary"
              label="Login"
              onClick={(event) => handleLogin(event)}
            >
              Login
            </Button>
            <Button
              variant="outlined"
              color="primary"
              label="SignUp"
              onClick={(event) => handleSignup(event)}
            >
              Signup
            </Button>
          </div>
        </Grid>
      </Grid>
    </div>
  );
};

Login.propTypes = {
  setAuth: PropTypes.func,
};

export default Login;
