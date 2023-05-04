import { Comment } from "./comment.entity";
import { Entity } from "./entity";

export class Entry implements Entity{
    constructor(
        public id: number,
        public fecha: Date,
        public titulo: string,
        public autor: string,
        public contenido: string,
        public comments: Comment[],
    ){}
    
}