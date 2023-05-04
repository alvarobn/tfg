import { Entity } from "../../domain/entities/entity";

export interface Repository<T extends Entity> {
    readAll(): Promise<T[]>;
    read(filter: any): Promise<T[]>;
    read_project(filter: any, projection: any): Promise<T[]>;
    read_sort(filter: any, sort: any): Promise<T[]>;
    read_sort_limit(filter: any, sort: any, limit: any): Promise<T[]>;
    create(entity: T): Promise<void>;
    countAll(): Promise<number>;
    update(filter: any, update: any): Promise<void>;
    replace(filter: any, update: any): Promise<void>;
    getOne(filter: any): Promise<T[]>;
    removeAll(): Promise<void>;
    getLast(filter: any): Promise<T[]>;
    store(newObject: any): Promise<void>;
}
