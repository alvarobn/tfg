const db = require('../db');
const helper = require('../helper');
const {User} = require('../../domain/entities/user.entity');
const bcryptjs = require('bcryptjs');

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

async function checkUser(username,password){
  
  const rows = await db.query(
    `SELECT * FROM users WHERE name = :name`,{name: username}
  );
  const data = helper.emptyOrRows(rows);
  if(data.length>0){
    const hash = data[0]['password_hash'];
    if(await bcryptjs.compare(password,hash)){
      console.log("USER PETITION => " + username);
      return true;
    }else return false;
  }
  else return false;

}

module.exports = {
  getAll,
  getById,
  checkUser
}