const mysql = require('mysql2/promise');
const process = require('process');

const config = {
  db: {
      host: process.env.MYSQLDB_HOST,
      port: process.env.MYSQLDB_DOCKER_PORT,
      user: process.env.MYSQLDB_USER,
      password: process.env.MYSQLDB_ROOT_PASSWORD,
      database: process.env.MYSQLDB_DATABASE,
  }
};

async function query(sql, params) {
  const connection = await mysql.createConnection(config.db);
  const [results, ] = await connection.execute(sql, params);

  return results;
}

module.exports = {
  query,
  config
}