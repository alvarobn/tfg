import { Response } from "./response";

export class CommentResponse implements Response {
    constructor(
        public fecha: Date,
        public autor: number,
        public entry: number,
        public contenido: string,
    ) {}
}

