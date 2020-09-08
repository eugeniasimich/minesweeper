const getCSRFToken = (setToken) => {
  return fetch(`/token`, {
    accept: "application/json",
  })
    .then(checkStatus)
    .then(parseJSON)
    .then(setToken);
};

const getNewGame = (nRows, nCols, nMines, setGame) => {
  return fetch(`/api/newGame/${nRows}/${nCols}/${nMines}`, {
    accept: "application/json",
  })
    .then(checkStatus)
    .then(parseJSON)
    .then(setGame);
};

const openCell = (row, col, game, csrfToken, setGame) => {
  const requestOptions = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      accept: "application/json",
      "Csrf-Token": csrfToken,
    },
    body: JSON.stringify({ g: game, i: row, j: col }),
  };
  return fetch("/api/openCell", requestOptions)
    .then(checkStatus)
    .then(parseJSON)
    .then(setGame);
};

const saveGame = (game, flags, seconds, csrfToken) => {
  return (name) => {
    const requestOptions = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        accept: "application/json",
        "Csrf-Token": csrfToken,
      },
      body: JSON.stringify({
        g: game,
        flags: flags,
        seconds: seconds,
        name: name,
      }),
    };
    return fetch("/api/saveGame", requestOptions).then(checkStatus);
  };
};

const getSavedGamesList = (setGame) => {
  return fetch("/api/savedGames", {
    accept: "application/json",
  })
    .then(parseJSON)
    .then(setGame);
};

const getSavedGame = (name, setGame) => {
  return fetch(`/api/resumeGame/${name}`, {
    accept: "application/json",
  })
    .then(parseJSON)
    .then(setGame);
};

const checkStatus = (response) => {
  if (response.status >= 200 && response.status < 300) {
    return response;
  }
  const error = new Error(`HTTP Error ${response.statusText}`);
  error.status = response.statusText;
  error.response = response;
  throw error;
};

const parseJSON = (response) => {
  return response.json();
};

const Client = {
  getCSRFToken,
  getNewGame,
  openCell,
  saveGame,
  getSavedGamesList,
  getSavedGame,
};
export default Client;
