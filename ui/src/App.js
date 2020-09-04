import React, { useState } from "react";
import { Grid } from "./Grid";
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

  const flagCell = (event, i, j) => {
    event.preventDefault();
    alert("right click on " + i + " " + j);
  };

  //const [flags, setFlags] = useState([]);
  const [game, setGame] = useState();

  return (
    <div>
      <h1>Welcome to Minesweeper!</h1>
      <SizeForm onConfirm={getGame} />
      {game && (
        <Grid
          onCellRightClick={flagCell}
          onCellClick={(i, j) => postOpenCell(i, j)}
          gridData={game.data}
        />
      )}
    </div>
  );
};

export default App;
