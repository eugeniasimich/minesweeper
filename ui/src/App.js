import React, { useState, useEffect, Fragment } from "react";
import { Grid } from "./Grid";
import { cellHasFlag } from "./flagUtils";
import { Menu } from "./Menu";
import { TimeTracker } from "./TimeTracker";
import "./App.css";
import Client from "./Client";
import Login from "./Login";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import PrivateRoute from "./PrivateRoute";

const App = () => {
  useEffect(() => Client.getCSRFToken(setCsrfToken), []);

  const flagCell = (event, row, col) => {
    event.preventDefault();
    if (!cellHasFlag(row, col, flags))
      setFlags(flags.concat([{ row: row, col: col }]));
    else setFlags(flags.filter((p) => !(p.row === row && p.col === col)));
  };

  const restoreGame = (savedGame) => {
    savedGame && setSeconds(savedGame.seconds);
    savedGame && setFlags(savedGame.flags);
    savedGame && setGame(savedGame.g);
    setGridDisabled(false);
    //TODO set name so it can be overwritten
  };
  const [flags, setFlags] = useState([]);
  const [game, setGame] = useState();
  const [seconds, setSeconds] = useState(0);
  const [csrfToken, setCsrfToken] = useState();
  const [gridDisabled, setGridDisabled] = useState(false);

  const openCellCB = (game) => {
    setGame(game);
    setGridDisabled(false);
  };
  const onCellClick = (i, j) => {
    setGridDisabled(true);
    Client.openCell(i, j, game, csrfToken, openCellCB);
  };

  const GamePage = (isAuthMode) => {
    return (
      <Fragment>
        <Menu
          isAuthMode={isAuthMode}
          onNewGame={(nRows, nCols, nMines) =>
            Client.getNewGame(nRows, nCols, nMines, (game) => {
              setGame(game);
              setFlags([]);
              setSeconds(0);
            })
          }
          onSaveGame={Client.saveGame(game, flags, seconds, csrfToken)}
          showSave={game && !game.hasWon && !game.hasLost}
          onResumeGameSelection={restoreGame}
        />
        {game && (
          <Grid
            onCellRightClick={flagCell}
            onCellClick={onCellClick}
            gridData={game.data}
            flags={flags}
            gridDisabled={gridDisabled}
          />
        )}
        {game && game.hasWon && <h1>You won!</h1>}
        {game && game.hasLost && <h1>You lost :(</h1>}

        {game && (
          <TimeTracker
            seconds={seconds}
            setSeconds={setSeconds}
            finished={game.hasWon || game.hasLost}
          ></TimeTracker>
        )}
      </Fragment>
    );
  };

  return (
    <div className="App">
      <h1>{"Welcome to Minesweeper!"}</h1>
      <Router>
        <div>
          <Switch>
            <Route path="/public">{GamePage(false)}</Route>
            <Route path="/login">
              <Login csrfToken={csrfToken} />
            </Route>
            <PrivateRoute path="/">{GamePage(true)}</PrivateRoute>
          </Switch>
        </div>
      </Router>
    </div>
  );
};

export default App;
