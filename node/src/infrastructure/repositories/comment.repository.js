const db = require('../db');
const helper = require('../helper');
const {Comment} = require('../../domain/entities/comment.entity');

async function getAll(){
  const rows = await db.query(
    `SELECT *
    FROM comments`
  );
  const data = helper.emptyOrRows(rows);
  let listComments = [];
  for(let i in data){
    const newComment = new Comment(data[i]['id'],data[i]['autor'],data[i]['contenido'],data[i]['fecha'],data[i]['entry_id'],data[i]['user_comment_id']);
    listComments.push(newComment);
  }
  return listComments;
}
async function getById(id){
  const rows = await db.query(
    `SELECT *
    FROM comments
    WHERE id=${id}`
  );
  const data = helper.emptyOrRows(rows);
  let listComments = [];
  for(let i in data){
    const newComment = new Comment(data[i]['id'],data[i]['autor'],data[i]['contenido'],data[i]['fecha'],data[i]['entry_id'],data[i]['user_comment_id']);
    listComments.push(newComment);
  }
  return listComments;
}


module.exports = {
  getAll,
  getById
}