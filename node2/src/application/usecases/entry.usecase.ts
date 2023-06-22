import { Comment } from "../../domain/entities/comment.entity";
import { Entry } from "../../domain/entities/entry.entity";
import { User } from "../../domain/entities/user.entity";
import { Repository } from "../../infrastructure/repositories/repository";
import { CommentResponse } from "../responses/comment.response";
import { EntryResponse } from "../responses/entry.response";
import { UserResponse } from "../responses/user.response";


export class EntryUseCase {
    constructor(private repository: Repository<Entry>){}


    entryToResponse(entry: Entry): EntryResponse{
        const toResponse: EntryResponse = {
            id: entry.id,
            fecha: entry.fecha,
            titulo: entry.titulo,
            autor: entry.autor,
            contenido: entry.contenido,
            comments: entry.comments,
        };
        return toResponse;
    }

    
    async getAll(): Promise<EntryResponse[]> {
        const allEntries: Entry[] = await this.repository.readAll();
        const allEntriesResponse: EntryResponse[] = allEntries.map((entry: Entry): EntryResponse => this.entryToResponse(entry));
        return allEntriesResponse;
    }


    async getEntries(autor: number, titulo: string): Promise<EntryResponse[]> {
        let filter = {};
        if(autor==-1 && titulo==""){
            filter = {};
        }else if(autor!=-1 && titulo==""){
            filter = {autor:autor};
        }else if(autor==-1 && titulo!=""){
            filter = {titulo:titulo};
        }else{
            filter = {$and:[{autor:autor},{titulo:titulo}]};
        }
        const allEntries: Entry[] = await this.repository.read(filter);
        const allEntriesResponse: EntryResponse[] = allEntries.map((entry: Entry): EntryResponse => this.entryToResponse(entry));
        return allEntriesResponse;
    }

}