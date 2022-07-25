import { rest } from 'msw';
import { WritingArticles } from '@/api/article';
import type { PathParams } from 'msw';
import mockData from '@/mock/data/detailArticle.json';
import { HOME_URL } from '@/constants/url';

interface WritingArticlesWithId extends WritingArticles {
	id: number;
}

const data = localStorage.getItem('mock-article');

const mockArticle = data ? (JSON.parse(data) as WritingArticlesWithId[]) : [];

export const ArticleHandler = [
	rest.post<{ title: string; content: string; category: string }, never, { id: number }>(
		`${HOME_URL}/api/articles`,
		(req, res, ctx) => {
			const { title, content, category } = req.body;

			localStorage.setItem(
				'mock-article',
				JSON.stringify(mockArticle.concat({ id: mockArticle.length, title, content, category })),
			);

			return res(ctx.status(201), ctx.json({ id: mockArticle.length, category }));
		},
	),

	rest.get(`${HOME_URL}/api/articles/:id`, (req, res, ctx) => {
		const { id } = req.params;

		if (typeof id !== 'string') {
			return;
		}

		// const filteredArticles = mockArticle.find((article) => article.id === Number(id));

		// if (filteredArticles === undefined) {
		// 	return;
		// }
		const filteredArticles = mockData.detailArticle;
		return res(
			ctx.status(200),
			ctx.json({
				title: filteredArticles.title,
				content: filteredArticles.content,
				isAuthor: true,
				views: 1,
				createdAt: '2022-07-12,13:04',
				author: {
					name: 'sming',
					avatarUrl:
						'https://avatars.githubusercontent.com/u/85891751?s=400&u=1d8557f04298a05f8a8bbceb9817b8a0089d63f8&v=4',
				},
			}),
		);
	}),

	rest.get(`${HOME_URL}/api/articles`, (req, res, ctx) => {
		const page = req.url.searchParams.get('page');
		const size = req.url.searchParams.get('size');

		const responseArticles = mockArticle.map((article) => ({
			id: article.id,
			title: article.title,
			content: article.content,
			isAuthor: true,
			views: 1,
			createdAt: '2022-07-12',
			author: {
				name: 'sming',
				avatarUrl:
					'https://avatars.githubusercontent.com/u/85891751?s=400&u=1d8557f04298a05f8a8bbceb9817b8a0089d63f8&v=4',
			},
		}));

		if (page === null || size === null) {
			return res(ctx.status(200), ctx.json({ articles: responseArticles }));
		}

		const articlesPage = responseArticles.filter(
			(responseArticle) =>
				responseArticle.id >= Number(page) * Number(size) &&
				responseArticle.id < Number(page) * Number(size + 1),
		);

		return res(
			ctx.status(200),
			ctx.json({
				articles: responseArticles,
				hasNext: responseArticles.length < Number(page) * Number(size + 1),
			}),
		);
	}),

	rest.put<{ title: string; content: string }, PathParams, { id: string }>(
		`${HOME_URL}/api/articles/:id`,
		(req, res, ctx) => {
			const { title, content } = req.body;
			const { id } = req.params;

			if (typeof id !== 'string') {
				return;
			}

			const article = mockArticle.find((article) => article.id === Number(id));

			if (article === undefined) {
				throw new Error('글을 찾을수 없습니다.');
			}
			article.title = title;
			article.content = content;

			localStorage.setItem('mock-article', JSON.stringify(mockArticle));

			return res(ctx.status(200), ctx.json({ id, category: article.category }));
		},
	),

	rest.delete<never, PathParams>(`${HOME_URL}/api/articles/:id`, (req, res, ctx) => {
		const { id } = req.params;

		if (typeof id !== 'string') {
			return;
		}

		const filteredArticles = mockArticle.filter((article) => article.id !== Number(id));

		localStorage.setItem('mock-article', JSON.stringify(filteredArticles));

		return res(ctx.status(204));
	}),
];
