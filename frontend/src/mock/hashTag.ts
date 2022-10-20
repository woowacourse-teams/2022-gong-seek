import { rest } from 'msw';

import { HOME_URL } from '@/constants/apiUrl';

export const HashTagHandler = [
	rest.get<{ tags: string[] }>(`${HOME_URL}/api/tags`, (req, res, ctx) =>
		res(
			ctx.status(200),
			ctx.json({
				tags: [
					'리액트',
					'해시태그',
					'프론트엔드',
					'백엔드',
					'스프링',
					'내일',
					'오늘',
					'스프린트3',
					'20자예시로넣는문구__ㅁㅁㅁㅁㅁㅁㅁㅁ',
				],
			}),
		),
	),
];
