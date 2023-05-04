import { Comment } from "../../domain/entities/comment.entity";
import { Entry } from "../../domain/entities/entry.entity";
import { User } from "../../domain/entities/user.entity";
import { Repository } from "../../infrastructure/repositories/repository";
import { CommentResponse } from "../responses/comment.response";
import { EntryResponse } from "../responses/entry.response";
import { UserResponse } from "../responses/user.response";


export class CommentUseCase {
    constructor(private repository: Repository<User>){}

    commentToResponse(comment: Comment): CommentResponse{
        const toResponse: CommentResponse = {
            fecha: comment.fecha,
            autor: comment.autor,
            contenido: comment.contenido,
        };
        return toResponse;
    }

    entryToResponse(entry: Entry): EntryResponse{
        const toResponse: EntryResponse = {
            fecha: entry.fecha,
            titulo: entry.titulo,
            autor: entry.autor,
            contenido: entry.contenido,
            comments: entry.comments.map(comment => this.commentToResponse(comment)),
        };
        return toResponse;
    }

    userToResponse(user: User): UserResponse {
        const response: UserResponse = {
            name: user.name,
            roles: user.roles,
            entries: user.entries.map(entry => this.entryToResponse(entry)),
            email: user.email,
        };
        return response;
    }

    
    async getAll(): Promise<CommentResponse[]> {
        const allUsers: User[] = await this.repository.read_project({},{_id:0,entries:1});
        let allEntries: Entry[] = [];
        allUsers.forEach(user => allEntries = allEntries.concat(user.entries));
        let allComments: CommentResponse[] = []
        allEntries.forEach(entry => allComments = allComments.concat(entry.comments.map(comment => this.commentToResponse(comment))));
        return allComments;
    }
}