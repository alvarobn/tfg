import { Router, Request, Response, RequestHandler } from "express";
import { MongoClient } from "mongodb";
import { createMongoClient} from "../config";
import { env } from "process";
import { User } from "../../domain/entities/user.entity";
import { Repository } from "../repositories/repository";
import { MongodbRepository } from "../repositories/mongodb.repository";
import { UserUseCase } from "../../application/usecases/user.usecase";
import { UserResponse } from "../../application/responses/user.response";


//MONGODB INIT
function initMongoDB(client: MongoClient): RequestHandler {
    return async function dbinit(req: Request, res: Response): Promise<Response> {
        try{
            const userRepository: Repository<User> = new MongodbRepository(client.db('spring').collection('users'))
            const admin = new User(1,"admin","pass",['ROLE_ADMIN','ROLE_USER'],[],"admin@gmail.com");
            if(await userRepository.countAll()==0){
                await userRepository.store(admin);
            }
            return res.json("MONGO INIT");
        }catch(err){
            console.error(err);
            return res.sendStatus(404);
        }
    }
}

//HANDLER FOR USERS
function createGetUsersHandler(userUseCase: UserUseCase): RequestHandler {
    return async function getAllUsersHandler(req: Request, res: Response): Promise<Response> {
        try{
            const allUsers: UserResponse[] = await userUseCase.getAll();
            return res.json(allUsers);
        }catch(err){
            console.error(err);
            return res.sendStatus(404);
        }
    }
}


function createCollectionsRouter(client: MongoClient): Router {
    
    const router = Router();
    const userUseCase: UserUseCase = new UserUseCase(new MongodbRepository(client.db("spring").collection("users")));
    
    router.get("/init", initMongoDB(client));
    router.get("/api/users", createGetUsersHandler(userUseCase));
    return router;
}

const user = env.MONGO_USERNAME ?? 'root';
const password = env.MONGO_PASSWORD ?? 'admin';
const host = 'mongodb'
const port = env.MONGO_PORT ?? '27017';
const client = createMongoClient(user, password, host, port);

const router = createCollectionsRouter(client);

export { router };
