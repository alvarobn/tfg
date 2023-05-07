import { CommentResponse } from "./comment.response";
import { Response } from "./response";

export class EntryResponse implements Response {
    constructor(
        public fecha: Date,
        public titulo: string,
        public autor: number,
        public contenido: string,
        public comments: number[],
    ) {}
}

