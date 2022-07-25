import { HOME_URL } from '@/constants/url';
import { rest } from 'msw';

export const LoginHandler = [
	rest.get(`${HOME_URL}/api/auth/github`, (req, res, ctx) => res(ctx.status(200))),
];
