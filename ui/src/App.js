import React from "react";
import { Grid } from "./Grid";
import { SizeForm } from "./SizeForm";

const App = () => {
  const getGame = (x, y, n) => {
    return fetch(`/api/newGame/${x}/${y}/${n}`, {
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
      <SizeForm onConfirm={getGame} />
      <Grid onCellClick={(i, j) => alert(i + "," + j)} gridData={data} />
    </div>
  );
};

export default App;
