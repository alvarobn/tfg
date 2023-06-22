const db = require('../db');
const helper = require('../helper');
const {Entry} = require('../../domain/entities/entry.entity');

async function getAll(){
  const rows = await db.query(
    `SELECT *
    FROM entries`
  );
  const data = helper.emptyOrRows(rows);
  let listEntries = [];
  for(let i in data){
    const newEntry = new Entry(data[i]['id'],data[i]['autor'],data[i]['titulo'],data[i]['contenido'],data[i]['descripcion'],
        data[i]['fecha'],data[i]['fecha'],data[i]['image_id'],data[i]['user_entry_id']);
    listEntries.push(newEntry);
  }
  return listEntries;
}
async function getById(id){
  const rows = await db.query(
    `SELECT *
    FROM entries
    WHERE id=${id}`
  );
  const data = helper.emptyOrRows(rows);
  let listEntries = [];
  for(let i in data){
    const newEntry = new Entry(data[i]['id'],data[i]['autor'],data[i]['titulo'],data[i]['contenido'],data[i]['descripcion'],
        data[i]['fecha'],data[i]['fecha'],data[i]['image_id'],data[i]['user_entry_id']);
    listEntries.push(newEntry);
  }
  return listEntries;
}

module.exports = {
  getAll,
  getById
}