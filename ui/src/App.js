import React, { useState } from "react";
import { Grid } from "./Grid";
import { cellHasFlag } from "./flagUtils";
import { Menu } from "./Menu";
import { TimeTracker } from "./TimeTracker";

const App = () => {
  const getGame = (x, y, n) => {
    setFlags([]);
    setStartDate(new Date().toLocaleString());
    return fetch(`/api/newGame/${x}/${y}/${n}`, {
      accept: "application/json",
    })
      .then((r) => {
        console.log(r);
        return r.json();
      })
      .then((j) => setGame(j));
  };

  const requestOpenCell = (i, j) => {
    const requestOptions = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        accept: "application/json",
      },
      body: JSON.stringify({ g: game, i: i, j: j }),
    };
    return fetch("/api/openCell", requestOptions)
      .then((r) => r.json())
      .then((j) => {
        console.log(j);
        setGame(j);
      });
  };

  const flagCell = (event, row, col) => {
    event.preventDefault();
    if (!cellHasFlag(row, col, flags))
      setFlags(flags.concat([{ row: row, col: col }]));
    else setFlags(flags.filter((p) => !(p.row === row && p.col === col)));
  };

  const requestSaveGame = (game, flags, seconds) => {
    return (name) => {
      const requestOptions = {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          accept: "application/json",
        },
        body: JSON.stringify({
          g: game,
          flags: flags,
          seconds: 34,
          name: name,
        }),
      };
      return fetch("/api/saveGame", requestOptions)
        .then((r) => r.json())
        .then((j) => {
          console.log(j);
        });
    };
  };

  const [flags, setFlags] = useState([]);
  const [game, setGame] = useState();
  const [startDate, setStartDate] = useState();
  return (
    <div>
      <h1>Welcome to Minesweeper!</h1>
      <Menu
        onNewGame={getGame}
        onSaveGame={requestSaveGame(game, flags, 1)}
        showSave={game}
      />
      {game &&
        (game.hasWon ? (
          <h1>You won!</h1>
        ) : game.hasLost ? (
          <h1>You lost :(</h1>
        ) : (
          <Grid
            onCellRightClick={flagCell}
            onCellClick={(i, j) => requestOpenCell(i, j)}
            gridData={game.data}
            flags={flags}
          />
        ))}
      {game && (
        <TimeTracker
          startDate={startDate}
          finished={game.hasWon || game.hasLost}
        ></TimeTracker>
      )}
    </div>
  );
};

export default App;
