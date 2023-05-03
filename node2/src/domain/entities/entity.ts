import { Document } from "mongodb";

export interface Entity extends Document {} //necesario para que mongodb devuelva el tipo correcto
