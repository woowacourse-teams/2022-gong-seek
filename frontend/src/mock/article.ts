import { rest } from 'msw';
import type { PathParams } from 'msw';

import { HOME_URL } from '@/constants/apiUrl';
import mockArticle from '@/mock/data/articles.json';

export const ArticleHandler = [
	rest.post<{ title: string; content: string; category: string }, never, { id: number }>(
		`${HOME_URL}/api/articles`,
		(req, res, ctx) => {
			const { category } = req.body;
			return res(ctx.status(201), ctx.json({ id: 1, category }));
		},
	),

	rest.get(`${HOME_URL}/api/articles/:id`, (req, res, ctx) => {
		const { id } = req.params;

		if (typeof id !== 'string') {
			return;
		}
		const filteredArticles = mockArticle.articles.find((article) => String(article.id) === id);

		if (typeof filteredArticles === 'undefined') {
			throw new Error('글을 찾지 못했습니다.');
		}

		return res(ctx.status(200), ctx.json(filteredArticles));
	}),

	rest.get(`${HOME_URL}/api/articles`, (req, res, ctx) => {
		const size = req.url.searchParams.get('pageSize');
		const category = req.url.searchParams.get('category');

		if (category === null || size === null) {
			return res(ctx.status(200), ctx.json({ articles: mockArticle.articles }));
		}
		const articlesPage = mockArticle.articles
			.map((article) => ({
				id: article.id,
				title: article.title,
				author: article.author,
				content: article.content,
				category: category,
				createdAt: article.createdAt,
				views: article.views,
				isLike: article.isLike,
				likeCount: article.likeCount,
			}))
			.slice(0, Number(size));

		return res(
			ctx.status(200),
			ctx.json({
				articles: articlesPage,
				hasNext: true,
			}),
		);
	}),

	rest.put<{ title: string; content: string }, PathParams, { id: string }>(
		`${HOME_URL}/api/articles/:id`,
		(req, res, ctx) => {
			const { id } = req.params;

			if (typeof id !== 'string') {
				return;
			}
			const article = mockArticle.articles.find((article) => article.id === Number(id));

			if (article === undefined) {
				throw new Error('글을 찾을수 없습니다.');
			}

			return res(ctx.status(200), ctx.json({ id, category: 'question' }));
		},
	),

	rest.delete<never, PathParams>(`${HOME_URL}/api/articles/:id`, (req, res, ctx) =>
		res(ctx.status(204)),
	),
];
