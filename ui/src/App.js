import React from "react";
import Button from "@material-ui/core/Button";

const App = () => {
  const getGame = () => {
    return fetch("/api/newGame", {
      accept: "application/json",
    })
      .then((r) => r.json())
      .then(console.log);
  };

  return (
    <div>
      <h1>Welcome to Minesweeper!</h1>
      <Button onClick={getGame}>Create New Game</Button>
    </div>
  );
};

export default App;
