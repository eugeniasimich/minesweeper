import React from "react";
import Button from "@material-ui/core/Button";
import { Grid } from "./Grid";

const App = () => {
  const getGame = () => {
    return fetch("/api/newGame", {
      accept: "application/json",
    })
      .then((r) => r.json())
      .then(console.log);
  };

  const data = [
    [
      {
        isOpen: true,
        isMine: true,
        isFlag: false,
        n: 0,
      },
      {
        isOpen: false,
        isMine: true,
        isFlag: false,
        n: 0,
      },
    ],
    [
      {
        isOpen: true,
        isMine: false,
        isFlag: false,
        n: 1,
      },
      {
        isOpen: false,
        isMine: false,
        isFlag: true,
        n: 1,
      },
    ],
  ];

  return (
    <div>
      <h1>Welcome to Minesweeper!</h1>
      <Button onClick={getGame}>Create New Game</Button>
      <Grid onCellClick={(i, j) => alert(i + "," + j)} gridData={data} />
    </div>
  );
};

export default App;
