import { CommentResponse } from "./comment.response";
import { Response } from "./response";

export class EntryResponse implements Response {
    constructor(
        public id: number,
        public fecha: Date,
        public titulo: string,
        public autor: string,
        public contenido: string,
        public descripcion: string,
        public comments: CommentResponse[],
    ) {}
}

