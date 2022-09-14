import { rest } from 'msw';

import { HOME_URL } from '@/constants/apiUrl';

export const LoginHandler = [
	rest.get(`${HOME_URL}/api/auth/github`, (req, res, ctx) => res(ctx.status(200))),
];
