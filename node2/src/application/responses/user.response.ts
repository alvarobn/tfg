import { EntryResponse } from "./entry.response";
import { Response } from "./response";

export class UserResponse implements Response {
    constructor(
        public id: number,
        public name: string,
        public entries: number[],
        public comments: number[],
        public email: string,
    ) {}
}

