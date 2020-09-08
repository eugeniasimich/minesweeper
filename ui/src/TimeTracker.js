import React, { useEffect } from "react";
import PropTypes from "prop-types";
export const TimeTracker = ({ seconds, setSeconds, finished }) => {
  useEffect(() => {
    let secTimer = setInterval(() => {
      setSeconds(seconds + 1);
    }, 1000);

    return () => clearInterval(secTimer);
  }, [seconds, setSeconds]);

  return !finished && <h2>Seconds: {seconds}</h2>;
};

TimeTracker.propTypes = {
  seconds: PropTypes.number,
  setSeconds: PropTypes.func,
  finished: PropTypes.bool,
};
