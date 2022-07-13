import { rest } from 'msw';
import { WritingArticles } from '@/api/article';
import type { PathParams } from 'msw';
const data = localStorage.getItem('mock-article');

const mockArticle = data ? (JSON.parse(data) as any[]) : [];

export const ArticleHandler = [
	rest.post<{ title: string; content: string; category: string }, never, { id: number }>(
		'http://192.168.0.155:8080/api/articles',
		(req, res, ctx) => {
			const { title, content, category } = req.body;

			localStorage.setItem(
				'mock-article',
				JSON.stringify(mockArticle.concat({ id: mockArticle.length, title, content, category })),
			);

			return res(ctx.status(200), ctx.json({ id: mockArticle.length }));
		},
	),

	rest.get('http://192.168.0.155:8080/api/articles/:id', (req, res, ctx) => {
		const category = req.url.searchParams.get('category');
		const { id } = req.params;

		const articles = JSON.parse(
			localStorage.getItem('mock-article') as string,
		) as WritingArticles[];

		const filteredArticles = articles.filter((article) => article);

		return res(
			ctx.status(200),
			ctx.json({
				title: filteredArticles.title,
				content: filteredArticles.content,
				isAuthor: true,
				views: 1,
				createdAt: '2022-07-12',
				author: {
					name: 'sming',
					avatarUrl:
						'https://avatars.githubusercontent.com/u/85891751?s=400&u=1d8557f04298a05f8a8bbceb9817b8a0089d63f8&v=4',
				},
			}),
		);
	}),

	rest.get('http://192.168.0.155:8080/api/articles', (req, res, ctx) => {
		const sort = req.url.searchParams.get('sort');
		const category = req.url.searchParams.get('category');
		const page = req.url.searchParams.get('page');
		const size = req.url.searchParams.get('size');

		const articles = JSON.parse(
			localStorage.getItem('mock-article') as string,
		) as WritingArticles[];

		const responseArticles = articles.map((article, idx) => ({
			id: idx,
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

		if (page === null) {
			return;
		}

		if (size === null) {
			return;
		}

		const articlesPage = responseArticles.filter(
			(responseArticle) =>
				responseArticle.id >= Number(page) * Number(size) &&
				responseArticle.id < Number(page) * Number(size + 1),
		);

		return res(ctx.status(200), ctx.json(articlesPage));
	}),

	rest.put<{ title: string; content: string }, PathParams, { id: string }>(
		'http://192.168.0.155:8080/api/articles/:id',
		(req, res, ctx) => {
			const { title, content } = req.body;
			const { id } = req.params;

			if (typeof id !== 'string') {
				return;
			}

			const articles = JSON.parse(
				localStorage.getItem('mock-article') as string,
			) as WritingArticles[];

			const article = articles.find((article) => article.id === id);
			if (article === undefined) {
				return;
			}
			article.title = title;
			article.content = content;

			localStorage.setItem('mock-article', JSON.stringify(articles));

			return res(ctx.status(200), ctx.json({ id }));
		},
	),

	rest.delete<never, PathParams>('http://192.168.0.155:8080/api/articles/:id', (req, res, ctx) => {
		const { id } = req.params;

		const articles = JSON.parse(
			localStorage.getItem('mock-article') as string,
		) as WritingArticles[];

		const filteredArticles = articles.filter((article) => article.id !== id);

		localStorage.setItem('mock-article', JSON.stringify(filteredArticles));

		return res(ctx.status(204));
	}),
];
