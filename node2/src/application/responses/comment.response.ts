import { Response } from "./response";

export class CommentResponse implements Response {
    constructor(
        public id: number,
        public fecha: Date,
        public autor: string,
        public contenido: string,
    ) {}
}

