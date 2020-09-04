export const cellHasFlag = (x, y, flags) => {
  const r = flags.some(({ row, col }) => row === x && col === y);
  console.log(x, y, r, flags);
  return r;
};
