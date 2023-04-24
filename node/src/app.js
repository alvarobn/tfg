const express = require('express');
const process = require('process');
const router = require("./infrastructure/routes/route");
const {basicAuth} = require("./application/security/basicauth");

const app = express();
const port = 3000;

app.use(basicAuth);
app.use(express.json());
app.use(express.urlencoded({extended: true,}));
app.use(router);

/* Error handler middleware */
app.use((err, req, res, next) => {
  const statusCode = err.statusCode || 500;
  console.error(err.message, err.stack);
  res.status(statusCode).json({ message: err.message });
  return;
});

app.listen(port, () => {
  console.log(`Example app listening at http://localhost:${port}`);
});