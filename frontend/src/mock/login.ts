import { rest } from 'msw';

export const LoginHandler = [
	rest.get('http://192.168.0.155:8080/api/auth/github', (req, res, ctx) => res(ctx.status(200))),
];
