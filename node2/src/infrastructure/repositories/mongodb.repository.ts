import { Repository } from "./repository";
import { Entity } from "../../domain/entities/entity";
import { Collection } from "mongodb";
import { genSaltSync, hashSync, compareSync } from "bcrypt";

export class MongodbRepository<E extends Entity> implements Repository<E> {
    constructor(
        private collection: Collection
    ) {}

    async readAll(): Promise<E[]> {
        const projection = { _id: 0 }; // eliminates _id field
        const allEntities = await this.collection.find().project<E>(projection).toArray();
        return allEntities;
    }

    async read(filter: any): Promise<E[]> {
        const projection = { _id: 0 }; // eliminates _id field
        const allEntities = await this.collection.find(filter).project<E>(projection).toArray();
        return allEntities;
    }
    
    async read_project(filter: any, projection: any): Promise<E[]> {
        const allEntities = await this.collection.find(filter).project<E>(projection).toArray();
        return allEntities;
    }

    async getLast(filter:any): Promise<E[]>{
        const projection = { _id: 0 }; // eliminates _id field
        const entry = await this.collection.find(filter).sort({"date":-1}).limit(1).project<E>(projection).toArray();
        return entry;
    }
    async read_sort(filter: any, sort: any): Promise<E[]> {
        const projection = { _id: 0 }; // eliminates _id field
        this.collection.createIndex(sort,{background:true})
        const allEntities = await this.collection.find(filter).sort(sort).project<E>(projection).toArray();
        return allEntities;
    }
    async read_sort_limit(filter: any, sort: any, limit: any): Promise<E[]> {
        const projection = { _id: 0 }; // eliminates _id field
        this.collection.createIndex(sort,{background:true});
        if(limit!=-1){
            return await this.collection.find(filter).sort(sort).limit(limit).project<E>(projection).toArray();
        }else{
            return await this.collection.find(filter).sort(sort).project<E>(projection).toArray();
        }
    }
    async create(entity: E): Promise<void> {
        const result = await this.collection.insertOne(entity as any);
        if (!result.acknowledged) throw new Error("Insert to mongodb error");
    }

    async countAll(): Promise<number> {
        const count = await this.collection.countDocuments();
        return count;
    }

    async update(filter: any, update: any): Promise<void> {
        const result = await this.collection.updateOne(filter, update);
        if (!result.acknowledged) throw new Error("Update to mongodb error");
    }

    async replace(filter: any, update: any): Promise<void> {
        const result = await this.collection.replaceOne(filter,update);
        if(!result.acknowledged) throw new Error("Replace to mongodb error");
    }

    async getOne(filter:any ): Promise<any[]> {
        const projection = {_id:0}
        const entry:any[] = await this.collection.find(filter).limit(1).project<E>(projection).toArray();
        return entry;
    }

    async removeAll(): Promise<void>{
        await this.collection.deleteMany({});
    }

    async store(newObject: any): Promise<void>{
        await this.collection.insertOne(newObject);
    }

    async checkUser(username: string, password: string): Promise<boolean> {
        const user:any = await this.collection.findOne({name:username});
        if(user!=null){
            if(compareSync(password,user.passwordHash)){
                console.log("USER PETITION => " + username);
                return true;
            }else return false;
        }else return false;
    }
}