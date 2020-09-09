import Cookies from "js-cookie";

const cookieName = "session-login";
const set = (token) => Cookies.set(cookieName, token);
const get = () => Cookies.get(cookieName);
const remove = () => Cookies.remove(cookieName);

const SessionCookie = {
  set,
  get,
  remove,
};
export default SessionCookie;
