import Cookies from "js-cookie";

const loginOrSignup = (
  username,
  password,
  csrfToken,
  onErrorCB,
  cb,
  isLogin
) => {
  const requestOptions = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      accept: "application/json",
      "Csrf-Token": csrfToken,
    },
    body: JSON.stringify({ username: username, password: password }),
  };
  return fetch(`/api/${isLogin ? "login" : "signup"}`, requestOptions)
    .then((response) => {
      if (
        response.status === 403 ||
        (response.status >= 200 && response.status < 300)
      ) {
        return response;
      }
      const error = new Error(`HTTP Error ${response.statusText}`);
      error.status = response.statusText;
      error.response = response;
      throw error;
    })
    .then(parseJSON)
    .then((json) => {
      if (json.message) return onErrorCB(json.message);
      else {
        Cookies.set("session-login", json);
        cb();
      }
    });
};

const isAuthenticated = () => {
  return Cookies.get("session-login");
};

const login = (username, password, csrfToken, onErrorCB, cb) => {
  loginOrSignup(username, password, csrfToken, onErrorCB, cb, true);
};

const signup = (username, password, csrfToken, onErrorCB, cb) => {
  loginOrSignup(username, password, csrfToken, onErrorCB, cb, false);
};

const parseJSON = (response) => {
  return response.json();
};

const signout = () => {
  Cookies.remove("session-login");
};

const Auth = {
  isAuthenticated,
  login,
  signup,
  signout,
};
export default Auth;
