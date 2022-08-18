import { rest } from 'msw';

import { HOME_URL } from '@/constants/url';
import { UserArticlesResponse } from '@/types/articleResponse';
import { Author } from '@/types/author';
import { UserCommentResponse } from '@/types/commentResponse';

export const MyPageHandler = [
	rest.get<Author>(`${HOME_URL}/api/members/me`, (req, res, ctx) =>
		res(
			ctx.status(200),
			ctx.json({
				name: '샐리',
				avatarUrl: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
			}),
		),
	),

	rest.get<UserArticlesResponse>(`${HOME_URL}/api/members/me/articles`, (req, res, ctx) =>
		res(
			ctx.status(200),
			ctx.json({
				articles: [
					{
						id: 1,
						title:
							'마이페이지에서 보여지는 글 제목 글제목이 20자 이상이 넘어갈 경우 어떻게 처리할 것인지 보기 위한 예시 문장입니다',
						category: 'question',
						commentCount: 2,
						createdAt: '2022-08-01T20:27',
						updatedAt: '',
						views: 10,
					},
					{
						id: 2,
						title:
							'마이페이지에서 보여지는 글 제목 글제목이 20자 이상이 넘어갈 경우 어떻게 처리할 것인지 보기 위한 예시 문장입니다',
						category: 'question',
						commentCount: 2,
						createdAt: '2022-08-01T20:27',
						updatedAt: '',
						views: 10,
					},
					{
						id: 3,
						title:
							'마이페이지에서 보여지는 글 제목 글제목이 20자 이상이 넘어갈 경우 어떻게 처리할 것인지 보기 위한 예시 문장입니다',
						category: 'question',
						commentCount: 2,
						createdAt: '2022-08-01T20:27',
						updatedAt: '',
						views: 10,
					},
				],
			}),
		),
	),

	rest.get<UserCommentResponse>(`${HOME_URL}/api/members/me/comments`, (req, res, ctx) =>
		res(
			ctx.status(200),
			ctx.json({
				comments: [
					{
						id: 1,
						content:
							'마이페이지에서 보여지는 글 제목 글제목이 20자 이상이 넘어갈 경우 어떻게 처리할 것인지 보기 위한 예시 문장입니다',
						createdAt: '2022-08-01T20:27',
						updatedAt: '',
					},
					{
						id: 2,
						content:
							'마이페이지에서 보여지는 글 제목 글제목이 20자 이상이 넘어갈 경우 어떻게 처리할 것인지 보기 위한 예시 문장입니다',
						createdAt: '2022-08-01T20:27',
						updatedAt: '',
					},
					{
						id: 3,
						content:
							'마이페이지에서 보여지는 글 제목 글제목이 20자 이상이 넘어갈 경우 어떻게 처리할 것인지 보기 위한 예시 문장입니다',
						createdAt: '2022-08-01T20:27',
						updatedAt: '',
					},
				],
			}),
		),
	),

	rest.put<{ name: string }>(`${HOME_URL}/api/members/me`, (req, res, ctx) => {
		const { name } = req.body;

		res(
			ctx.status(200),
			ctx.json({
				name,
				avatarUrl: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
			}),
		);
	}),
];
