import React, { useState } from "react";
import { Grid } from "./Grid";
import { cellHasFlag } from "./flagUtils";
import { SizeForm } from "./SizeForm";

const App = () => {
  const getGame = (x, y, n) => {
    return fetch(`/api/newGame/${x}/${y}/${n}`, {
      accept: "application/json",
    })
      .then((r) => {
        console.log(r);
        return r.json();
      })
      .then((j) => setGame(j));
  };

  const postOpenCell = (i, j) => {
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

  const [flags, setFlags] = useState([]);
  const [game, setGame] = useState();

  return (
    <div>
      <h1>Welcome to Minesweeper!</h1>
      <SizeForm onConfirm={getGame} />
      {game &&
        (game.hasWon ? (
          <h1>You won!</h1>
        ) : game.hasLost ? (
          <h1>You lost :(</h1>
        ) : (
          <Grid
            onCellRightClick={flagCell}
            onCellClick={(i, j) => postOpenCell(i, j)}
            gridData={game.data}
            flags={flags}
          />
        ))}
    </div>
  );
};

export default App;
