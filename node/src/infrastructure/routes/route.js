const express = require('express');
const useCaseUser = require('../../application/usecases/users.usecase.js');
const useCaseEntry = require('../../application/usecases/entry.usecase.js');
const useCaseComment = require('../../application/usecases/comment.usecase.js');

//USERS
async function getAllUsersHandler(req, res, next){
  try{
    res.json(await useCaseUser.getAll())
  }catch (err){
    console.error(`Error while getting users `, err.message);
    next(err);
  }
}
async function getUserByIdHandler(req, res, next){
  try{
    const id = req.params.id ?? -1;
    res.json(await useCaseUser.getById(id));
  }catch (err){
    console.error(`Error while getting users `, err.message);
    next(err);
  }
}
//ENTRIES
async function getAllEntriesHandler(req, res, next){
  try{
    res.json(await useCaseEntry.getAll())
  }catch (err){
    console.error(`Error while getting entries `, err.message);
    next(err);
  }
}
async function getEntryByIdHandler(req, res, next){
  try{
    const id = req.params.id ?? -1;
    res.json(await useCaseEntry.getById(id))
  }catch (err){
    console.error(`Error while getting entries `, err.message);
    next(err);
  }
}
//COMMENTS
async function getAllCommentsHandler(req, res, next){
  try{
    res.json(await useCaseComment.getAll())
  }catch (err){
    console.error(`Error while getting comments `, err.message);
    next(err);
  }
}
async function getCommentByIdHandler(req, res, next){
  try{
    const id = req.params.id ?? -1;
    res.json(await useCaseEntry.getById(id))
  }catch (err){
    console.error(`Error while getting entries `, err.message);
    next(err);
  }
}




function createCollectionsRouter(){
  let router = express.Router();

  router.get("/users",getAllUsersHandler);
  router.get("/users/:id",getUserByIdHandler);
  router.get("/entries",getAllEntriesHandler);
  router.get("/entries/:id",getEntryByIdHandler);
  router.get("/comments",getAllCommentsHandler);
  router.get("/comments/:id",getCommentByIdHandler);

  return router;
}

module.exports = createCollectionsRouter();