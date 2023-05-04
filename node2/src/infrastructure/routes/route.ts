import { Router, Request, Response, RequestHandler } from "express";
import { MongoClient } from "mongodb";
import { createMongoClient} from "../config";
import { env } from "process";
import { User } from "../../domain/entities/user.entity";
import { Repository } from "../repositories/repository";
import { MongodbRepository } from "../repositories/mongodb.repository";
import { UserUseCase } from "../../application/usecases/user.usecase";
import { UserResponse } from "../../application/responses/user.response";
import { Entry } from "../../domain/entities/entry.entity";
import { Comment } from "../../domain/entities/comment.entity";
import { EntryResponse } from "../../application/responses/entry.response";
import { EntryUseCase } from "../../application/usecases/entry.usecase";
import { CommentUseCase } from "../../application/usecases/comment.usecase";
import { CommentResponse } from "../../application/responses/comment.response";


//MONGODB INIT
function initMongoDB(client: MongoClient): RequestHandler {
    return async function dbinit(req: Request, res: Response): Promise<Response> {
        try{
            const userRepository: Repository<User> = new MongodbRepository(client.db('spring').collection('users'))
            const comment1 = new Comment(1,new Date(),"alvaro","Muy buen contenido");
            const entry1 = new Entry(1,new Date(),"titulo1","admin","Holaaaaaaaaaaaa",[comment1]);
            const admin = new User(1,"admin","pass",['ROLE_ADMIN','ROLE_USER'],[entry1],"admin@gmail.com");
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
            const name = req.query.name as string ?? "";
            if(name == ""){
                const allUsers: UserResponse[] = await userUseCase.getAll();
                return res.json(allUsers);
            }else{
                const user: UserResponse[] = await userUseCase.getByName(name);
                return res.json(user);
            }
        }catch(err){
            console.error(err);
            return res.sendStatus(404);
        }
    }
}

//HANDLER FOR ENTRIES
function createGetEntriesHandler(entryUseCase: EntryUseCase): RequestHandler {
    return async function getAllEntriesHandler(req: Request, res: Response): Promise<Response> {
        try{
            const autor = req.query.autor as string ?? "";
            const titulo = req.query.titulo as string ?? "";
            const allEntries: EntryResponse[] = await entryUseCase.getEntries(autor, titulo);
            return res.json(allEntries);
        }catch(err){
            console.error(err);
            return res.sendStatus(404);
        }
    }
}

//HANDLER FOR COMMENTS
function createGetCommentsHandler(commentUseCase: CommentUseCase): RequestHandler {
    return async function getAllCommentsHandler(req: Request, res: Response): Promise<Response> {
        try{
            const allComments: CommentResponse[] = await commentUseCase.getAll();
            return res.json(allComments);
        }catch(err){
            console.error(err);
            return res.sendStatus(404);
        }
    }
}


function createCollectionsRouter(client: MongoClient): Router {
    
    const router = Router();
    const userUseCase: UserUseCase = new UserUseCase(new MongodbRepository(client.db("spring").collection("users")));
    const entryUseCase: EntryUseCase = new EntryUseCase(new MongodbRepository(client.db("spring").collection("users")));
    const commentUseCase: CommentUseCase = new CommentUseCase(new MongodbRepository(client.db("spring").collection("users")));
    
    router.get("/init", initMongoDB(client));
    router.get("/api/users", createGetUsersHandler(userUseCase));
    router.get("/api/entries", createGetEntriesHandler(entryUseCase));
    router.get("/api/comments", createGetCommentsHandler(commentUseCase));
    return router;
}

const user = env.MONGO_USERNAME ?? 'root';
const password = env.MONGO_PASSWORD ?? 'admin';
const host = 'mongodb'
const port = env.MONGO_PORT ?? '27017';
const client = createMongoClient(user, password, host, port);

const router = createCollectionsRouter(client);

export { router };
