import React, { useState } from "react";
import { Grid } from "./Grid";
import { SizeForm } from "./SizeForm";

const App = () => {
  const getGame = (x, y, n) => {
    return fetch(`/api/newGame/${x}/${y}/${n}`, {
      accept: "application/json",
    })
      .then((r) => r.json())
      .then((j) => j.data && setData(j.data));
  };

  //const [flags, setFlags] = useState([]);
  const [data, setData] = useState([]);

  return (
    <div>
      <h1>Welcome to Minesweeper!</h1>
      <SizeForm onConfirm={getGame} />
      <Grid onCellClick={(i, j) => alert(i + "," + j)} gridData={data} />
    </div>
  );
};

export default App;
