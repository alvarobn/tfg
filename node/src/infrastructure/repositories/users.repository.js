const db = require('../db');
const helper = require('../helper');
const {User} = require('../../domain/entities/user.entity');

async function getAll(){
  const rows = await db.query(
    `SELECT *
    FROM users`
  );
  const data = helper.emptyOrRows(rows);
  let listUsers = [];
  for(let i in data){
    const newUser = new User(data[i]['id'],data[i]['name'],data[i]['email'],data[i]['password_hash']);
    listUsers.push(newUser);
  }
  return listUsers;
}
async function getById(id){
  const rows = await db.query(
    `SELECT *
    FROM users
    WHERE id=${id}`
  );
  const data = helper.emptyOrRows(rows);
  let listUsers = [];
  for(let i in data){
    const newUser = new User(data[i]['id'],data[i]['name'],data[i]['email'],data[i]['password_hash']);
    listUsers.push(newUser);
  }
  return listUsers;
}

module.exports = {
  getAll,
  getById
}