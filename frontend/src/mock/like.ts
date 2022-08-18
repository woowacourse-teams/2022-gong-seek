import { rest } from 'msw';

import { HOME_URL } from '@/constants/url';
import mockArticle from '@/mock/data/articles.json';

export const LikeHandler = [
	rest.post(`${HOME_URL}/api/articles/:articleId/like`, (req, res, ctx) => {
		const { articleId } = req.params;

		const filteredArticles = mockArticle.articles.find(
			(article) => String(article.id) === articleId,
		);

		if (typeof filteredArticles === 'undefined') {
			throw new Error('글을 찾지 못했습니다.');
		}

		filteredArticles.isLike = true;
		filteredArticles.likeCount += 1;

		return res(ctx.status(201), ctx.json({ isLike: true, likeCount: filteredArticles.likeCount }));
	}),

	rest.delete(`${HOME_URL}/api/articles/:articleId/unlike`, (req, res, ctx) => {
		const { articleId } = req.params;

		const filteredArticles = mockArticle.articles.find(
			(article) => String(article.id) === articleId,
		);

		if (typeof filteredArticles === 'undefined') {
			throw new Error('글을 찾지 못했습니다.');
		}

		filteredArticles.isLike = true;
		filteredArticles.likeCount -= 1;

		return res(ctx.status(201), ctx.json({ isLike: false, likeCount: filteredArticles.likeCount }));
	}),
];
