import { Response } from "./response";

export class CommentResponse implements Response {
    constructor(
        public fecha: Date,
        public autor: string,
        public contenido: string,
    ) {}
}

