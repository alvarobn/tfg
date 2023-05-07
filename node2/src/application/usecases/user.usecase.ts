import { Comment } from "../../domain/entities/comment.entity";
import { Entry } from "../../domain/entities/entry.entity";
import { User } from "../../domain/entities/user.entity";
import { Repository } from "../../infrastructure/repositories/repository";
import { CommentResponse } from "../responses/comment.response";
import { EntryResponse } from "../responses/entry.response";
import { UserResponse } from "../responses/user.response";


export class UserUseCase {
    constructor(private repository: Repository<User>){}

    userToResponse(user: User): UserResponse {
        const response: UserResponse = {
            name: user.name,
            roles: user.roles,
            entries: user.entries,
            comments: user.comments,
            email: user.email,
        };
        return response;
    }


    async checkUser(username: string, password:string): Promise<boolean>{
        if(await this.repository.checkUser(username, password)) return true;
        else return false;
      }
      
    async getAll(): Promise<UserResponse[]> {
        const allUsers: User[] = await this.repository.readAll();
        const allResponses = allUsers.map((user: User): UserResponse => this.userToResponse(user));
        return allResponses;
    }
    async getByName(nameUser: string): Promise<UserResponse[]> {
        const user: User[] = await this.repository.getOne({name:nameUser});
        const allUsers: UserResponse[] = user.map(user => this.userToResponse(user));
        return allUsers;
    }
}