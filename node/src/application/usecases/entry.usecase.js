const entryRepository = require('../../infrastructure/repositories/entries.repository');
const {EntryResponse} = require('../../application/response/entry.respose')

async function getAll(){
  const entries = await entryRepository.getAll();
  let listEntriesResponse = []
  for(let i in entries){
    const entryResponse = new EntryResponse(entries[i].id,entries[i].autor, entries[i].titulo,entries[i].contenido,entries[i].descripcion,entries[i].fecha);
    listEntriesResponse.push(entryResponse);
  }
  return listEntriesResponse;
}
async function getById(id){
  const entries = await entryRepository.getById(id);
  let listEntriesResponse = []
  for(let i in entries){
    const entryResponse = new EntryResponse(entries[i].id,entries[i].autor, entries[i].titulo,entries[i].contenido,entries[i].descripcion,entries[i].fecha);
    listEntriesResponse.push(entryResponse);
  }
  return listEntriesResponse;
}


module.exports = {
  getAll,
  getById
}