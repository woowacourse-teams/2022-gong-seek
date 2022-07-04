import { rest } from 'msw';

export const LoginHandler = [
	rest.get('/api/auth/github', (req, res, ctx) => {
		return res(ctx.status(200));
	}),
];
