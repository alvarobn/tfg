import { Entity } from "./entity";

export class Comment implements Entity{
    constructor(
        public id: number,
        public fecha: Date,
        public autor: string,
        public contenido: string,
    ){}
    
}