import { Entity } from "./entity";
import { genSaltSync, hashSync } from "bcrypt";

export class User implements Entity{
    constructor(
        public id: number,
        public name: string,
        public passwordHash: string,
        public roles: string[],
        public entries: number[],
        public comments: number[],
        public email: string,
    ){  
        const salt = genSaltSync(10);
        const hash = hashSync(passwordHash,salt);
        this.passwordHash = hash;
    }
    
}