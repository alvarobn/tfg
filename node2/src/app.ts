import express from 'express';
import { router } from "./infrastructure/routes/route"
import { basicAuth } from './application/security/basicauth';

const app = express();
app.use(basicAuth);
app.use(express.json());
app.use(router);
app.listen(3001, () => { console.log('App runing in port 3001') });
