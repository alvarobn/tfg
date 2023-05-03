import { MongoClient } from "mongodb";

export function createMongoClient(user: string, password: string, host: string, port: string): MongoClient{
    const connectionUrl = `mongodb://${user}:${password}@${host}:${port}`;
    return new MongoClient(connectionUrl);
}

