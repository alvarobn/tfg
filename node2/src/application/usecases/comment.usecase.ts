import { Comment } from "../../domain/entities/comment.entity";
import { Entry } from "../../domain/entities/entry.entity";
import { User } from "../../domain/entities/user.entity";
import { Repository } from "../../infrastructure/repositories/repository";
import { CommentResponse } from "../responses/comment.response";


export class CommentUseCase {
    constructor(private repository: Repository<Comment>){}

    commentToResponse(comment: Comment): CommentResponse{
        const toResponse: CommentResponse = {
            id: comment.id,
            fecha: comment.fecha,
            autor: comment.autor,
            entry: comment.entry,
            contenido: comment.contenido,
        };
        return toResponse;
    }
    
    async getAll(): Promise<CommentResponse[]> {
        const allComments: Comment[] = await this.repository.readAll();
        const allCommentsResponse: CommentResponse[] = allComments.map((comment: Comment): CommentResponse => this.commentToResponse(comment));
        return allCommentsResponse;
    }

    async getComments(autor: number, entry: number): Promise<CommentResponse[]> {
        let filter = {};
        if(autor==-1 && entry==-1){
            filter = {};
        }else if(autor!=-1 && entry==-1){
            filter = {autor:autor};
        }else if(autor==-1 && entry!=-1){
            filter = {entry:entry};
        }else{
            filter = {$and:[{autor:autor},{entry:entry}]};
        }
        const allComments: Comment[] = await this.repository.read(filter);
        const allCommentsResponse: CommentResponse[] = allComments.map((comment: Comment): CommentResponse => this.commentToResponse(comment));
        return allCommentsResponse;
    }
}