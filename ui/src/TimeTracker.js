import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";

export const TimeTracker = ({ startDate, finished }) => {
  const [currentTime, setCurrentTime] = useState(startDate);

  useEffect(() => {
    let secTimer = setInterval(() => {
      setCurrentTime(new Date().toLocaleString());
    }, 1000);

    return () => clearInterval(secTimer);
  }, []);

  const time = () => {
    const date1 = new Date(startDate);
    const date2 = new Date(currentTime);
    return (date2 - date1) / 1000;
  };

  return !finished && <h2>Seconds: {time()}</h2>;
};

TimeTracker.propTypes = {
  startDate: PropTypes.instanceOf(Date),
  finished: PropTypes.bool,
};
