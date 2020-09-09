import Cookies from "js-cookie";

const cookieName = "session-login";
const set = (session) =>
  Cookies.set(cookieName, session.token, {
    expires: new Date(session.expires),
    sameSite: "lax",
    secure: true,
  });
const get = () => Cookies.get(cookieName);
const remove = () => Cookies.remove(cookieName);

const SessionCookie = {
  set,
  get,
  remove,
};
export default SessionCookie;
