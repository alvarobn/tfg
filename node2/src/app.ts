import express from 'express';
import { router } from "./infrastructure/routes/route"

const app = express();
app.use(express.json());
app.use(router);
app.listen(3001, () => { console.log('App runing in port 3001') });
