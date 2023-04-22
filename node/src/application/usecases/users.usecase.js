const userRepository = require('../../infrastructure/repositories/users.repository');
const {UserResponse} = require('../../application/response/user.response')

async function getAll(){
  const users = await userRepository.getAll();
  let listUsersResponse = []
  for(let i in users){
    const userResponse = new UserResponse(users[i].id,users[i].name,users[i].email)
    listUsersResponse.push(userResponse);
  }
  return listUsersResponse;
}
async function getById(id){
  const users = await userRepository.getById(id);
  let listUsersResponse = []
  for(let i in users){
    const userResponse = new UserResponse(users[i].id,users[i].name,users[i].email)
    listUsersResponse.push(userResponse);
  }
  return listUsersResponse;
}



module.exports = {
  getAll,
  getById
}