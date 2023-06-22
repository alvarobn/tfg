const commentRepository = require('../../infrastructure/repositories/comment.repository');
const {CommentResponse} = require('../../application/response/comment.response')

async function getAll(){
  const comments = await commentRepository.getAll();
  let listCommentsResponse = []
  for(let i in comments){
    const commentResponse = new CommentResponse(comments[i].id,comments[i].autor,comments[i].contenido,comments[i].fecha,comments[i].entry_id,comments[i].user_comment_id);
    listCommentsResponse.push(commentResponse);
  }
  return listCommentsResponse;
}
async function getById(id){
  const comments = await commentRepository.getById(id);
  let listCommentsResponse = []
  for(let i in comments){
    const commentResponse = new CommentResponse(comments[i].id,comments[i].autor,comments[i].contenido,comments[i].fecha,comments[i].entry_id,comments[i].user_comment_id);
    listCommentsResponse.push(commentResponse);
  }
  return listCommentsResponse;
}

module.exports = {
  getAll,
  getById
}