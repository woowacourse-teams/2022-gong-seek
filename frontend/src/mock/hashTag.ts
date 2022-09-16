import { rest } from 'msw';

import { HOME_URL } from '@/constants/apiUrl';
import { CommonArticleType } from '@/types/articleResponse';

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
	rest.get<{ articles: CommonArticleType[] }>(`${HOME_URL}/api/articles/tags`, (req, res, ctx) => {
		res(
			ctx.status(200),
			ctx.json({
				articles: [
					{
						id: 1,
						title: '게시글 상세 조회 예시 타이틀',
						author: {
							name: '샐리',
							avatarUrl: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
						},
						content: '게시글 상세 조회 예시 markdown이 보여질 곳 ',
						category: 'discussion',
						views: 10,
						isAuthor: true,
						createdAt: '2022-07-19T13:32',
						updatedAt: '2022-07-29T16:55',
						tag: ['리액트', '프론트엔드'],
						likeCount: 10,
						commentCount: 10,
						isLike: true,
					},
				],
			}),
		);
	}),
];
