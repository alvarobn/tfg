const userUsecase = require("../usecases/users.usecase");

async function basicAuth(req, res, next) {

    if (!req.headers.authorization || req.headers.authorization.indexOf('Basic ') === -1) {
        return res.status(401).json({ message: 'Missing Authorization Header' });
    }

    const base64Credentials =  req.headers.authorization.split(' ')[1];
    const credentials = Buffer.from(base64Credentials, 'base64').toString('ascii');
    const [username, password] = credentials.split(':');

    if(!await userUsecase.checkUser(username,password)){
        return res.status(401).json({ message: 'Invalid Authentication Credentials' });
    }

    next();
}

module.exports = {
    basicAuth
  }