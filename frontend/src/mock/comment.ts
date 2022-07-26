import { rest } from 'msw';

import { HOME_URL } from '@/constants/url';
import mockData from '@/mock/data/comment.json';
import { CommentType } from '@/types/commentResponse';

const data = localStorage.getItem('mock-comments');

const mockComments = data ? (JSON.parse(data) as CommentType[][]) : [];

export const CommentHandler = [
	rest.post<{ content: string }>(
		`${HOME_URL}/api/articles/:articleId/comments`,
		(req, res, ctx) => {
			const { articleId } = req.params;
			const { content } = req.body;

			if (typeof articleId !== 'string') {
				return;
			}

			mockComments[Number(articleId)] = !mockComments[Number(articleId)]
				? []
				: mockComments[Number(articleId)];
			mockComments[Number(articleId)].push({
				id: mockComments[Number(articleId)].length,
				content,
				author: {
					name: 'sally',
					avatarUrl: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
				},
				createdAt: '2022-07-28',
				isAuthor: true,
			});
			localStorage.setItem('mock-comments', JSON.stringify(mockComments));

			return res(ctx.status(201));
		},
	),

	rest.get(`${HOME_URL}/api/articles/:articleId/comments`, (req, res, ctx) => {
		const { articleId } = req.params;
		return res(ctx.status(200), ctx.json({ comments: mockData.comments }));
	}),

	rest.put<{ content: string }>(
		`${HOME_URL}/api/articles/:articleId/comments/:commentId`,
		(req, res, ctx) => {
			const { articleId, commentId } = req.params;
			const { content } = req.body;

			const comment = mockComments[Number(articleId)].find(
				(mockComment) => mockComment.id === Number(commentId),
			);

			if (typeof comment === 'undefined') {
				throw new Error('댓글을 찾지 못하였습니다.');
			}

			comment.content = content;

			localStorage.setItem('mock-comments', JSON.stringify(mockComments));
			return res(ctx.status(200));
		},
	),

	rest.delete(`${HOME_URL}/api/articles/:articleId/comments/:commentId`, (req, res, ctx) => {
		const { articleId, commentId } = req.params;

		const filteredComments = mockComments[Number(articleId)].filter(
			(mockComment) => mockComment.id !== Number(commentId),
		);
		mockComments[Number(articleId)] = filteredComments;

		localStorage.setItem('mock-comments', JSON.stringify(mockComments));
		return res(ctx.status(204));
	}),
];
