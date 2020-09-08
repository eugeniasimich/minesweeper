const Auth = {
  isAuthenticated: false,
  authenticate(cb) {
    Auth.isAuthenticated = true;
    setTimeout(cb, 100); // fake async
  },
  signout(cb) {
    Auth.isAuthenticated = false;
    setTimeout(cb, 100);
  },
};
export default Auth;
