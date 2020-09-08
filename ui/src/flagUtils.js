export const cellHasFlag = (x, y, flags) => {
  const r = flags.some(({ row, col }) => row === x && col === y);
  return r;
};
