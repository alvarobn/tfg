import { createMongoClient } from "../../infrastructure/config";
import { MongodbRepository } from "../../infrastructure/repositories/mongodb.repository";
import { UserUseCase } from "../usecases/user.usecase";
import { env } from "process";

export async function basicAuth(req:any , res: any , next: any) {
    if (!req.headers.authorization || req.headers.authorization.indexOf('Basic ') === -1) {
        return res.status(401).json({ message: 'Missing Authorization Header' });
    }

    const base64Credentials =  req.headers.authorization.split(' ')[1];
    const credentials = Buffer.from(base64Credentials, 'base64').toString('ascii');
    const [username, password] = credentials.split(':');

    const userDB = env.MONGO_USERNAME ?? 'root';
    const passwordDB = env.MONGO_PASSWORD ?? 'admin';
    const hostDB = 'mongodb'
    const portDB = env.MONGO_PORT ?? '27017';
    const client = createMongoClient(userDB, passwordDB, hostDB, portDB);
    const userUseCase: UserUseCase = new UserUseCase(new MongodbRepository(client.db("spring").collection("users")));
    if(!await userUseCase.checkUser(username,password)){
        return res.status(401).json({ message: 'Invalid Authentication Credentials' });
    }
    next();
}
