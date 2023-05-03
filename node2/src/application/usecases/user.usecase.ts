import { User } from "../../domain/entities/user.entity";
import { Repository } from "../../infrastructure/repositories/repository";
import { UserResponse } from "../responses/user.response";


export class UserUseCase {
    constructor(private repository: Repository<User>){}


    userToResponse(user: User): UserResponse {
        const response: UserResponse = {
            name: user.name,
            roles: user.roles,
            entries: user.entries,
            email: user.email,
        };
        return response;
    }

    
    async getAll(): Promise<UserResponse[]> {
        const allUsers: User[] = await this.repository.readAll();
        const allResponses = allUsers.map((user: User): UserResponse => this.userToResponse(user));
        return allResponses;
    }
}