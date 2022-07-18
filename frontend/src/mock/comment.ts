import { CommentsResponse } from '@/api/comments';
import { rest } from 'msw';

const data = localStorage.getItem('mock-comments');

const mockComments = data ? (JSON.parse(data) as CommentsResponse[][]) : [];

export const CommentHandler = [
	rest.post<{ content: string }>(
		'http://192.168.0.155:8080/api/articles/:articleId/comments',
		(req, res, ctx) => {
			const { articleId } = req.params;
			const { content } = req.body;

			if (typeof articleId !== 'string') {
				return;
			}

			localStorage.setItem(
				'mock-comments',
				JSON.stringify(
					mockComments[Number(articleId)].concat({
						id: mockComments.length,
						content,
						authorName: '스밍',
						authorAvartarUrl:
							'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
						createdAt: '2022-07-28',
					}),
				),
			);

			return res(ctx.status(201));
		},
	),

	rest.get('http://192.168.0.155:8080/api/articles/:articleId/comments', (req, res, ctx) => {
		const { articleId } = req.params;

		return res(ctx.status(200), ctx.json(mockComments[Number(articleId)]));
	}),

	rest.put<{ content: string }>(
		'http://192.168.0.155:8080/api/articles/:articleId/comments/:commentId',
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

	rest.delete(
		'http://192.168.0.155:8080/api/articles/:articleId/comments/:commentId',
		(req, res, ctx) => {
			const { articleId, commentId } = req.params;

			const filteredComments = mockComments[Number(articleId)].filter(
				(mockComment) => mockComment.id !== Number(commentId),
			);

			localStorage.setItem('mock-comments', JSON.stringify(filteredComments));
			return res(ctx.status(204));
		},
	),
];
