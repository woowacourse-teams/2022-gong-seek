import { rest } from 'msw';
import type { PathParams } from 'msw';

import { WritingArticles } from '@/api/article';
import { HOME_URL } from '@/constants/url';
import mockData from '@/mock/data/detailArticle.json';
import mockArticle from '@/mock/data/articles.json';

interface WritingArticlesWithId extends WritingArticles {
	id: number;
}

export const ArticleHandler = [
	rest.post<{ title: string; content: string; category: string }, never, { id: number }>(
		`${HOME_URL}/api/articles`,
		(req, res, ctx) => {
			const {  category } = req.body;
			return res(ctx.status(201), ctx.json({ id: 1, category }));
		},
	),

	rest.get(`${HOME_URL}/api/articles/:id`, (req, res, ctx) => {
		const { id } = req.params;

		if (typeof id !== 'string') {
			return;
		}
		const filteredArticles = mockData.detailArticle;
		return res(
			ctx.status(200),
			ctx.json({
				id: id,
				title: filteredArticles.title,
				content: filteredArticles.content,
				isAuthor: true,
				views: 1,
				createdAt: '2022-07-12T13:04',
				updatedAt: '2022-07-29T16:43',
				author: {
					name: 'sming',
					avatarUrl:
						'https://avatars.githubusercontent.com/u/85891751?s=400&u=1d8557f04298a05f8a8bbceb9817b8a0089d63f8&v=4',
				},
			}),
		);
	}),

	rest.get(`${HOME_URL}/api/articles`, (req, res, ctx) => {
		const size = req.url.searchParams.get('size');
		const category = req.url.searchParams.get('category');


		if (category === null || size === null) {
			return res(ctx.status(200), ctx.json({ articles: mockArticle.articles }));
		}
		const articlesPage = mockArticle.articles.map(article => 
			({
				id: article.id,
				title: article.title,
				author: article.author,
				content: article.content,
				category: category,
				createdAt: article.createdAt,
				updatedAt: article.updatedAt,
				views: article.views, 
			} )
		).slice(0,Number(size));

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

			return res(ctx.status(200), ctx.json({ id, category: 'error' }));
		},
	),

	rest.delete<never, PathParams>(`${HOME_URL}/api/articles/:id`, (req, res, ctx) => res(ctx.status(204))),
];
