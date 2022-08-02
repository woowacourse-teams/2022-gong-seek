import axios from 'axios';

import { HOME_URL } from '@/constants/url';
import { AllArticleResponse, ArticleType, CommonArticleType } from '@/types/articleResponse';

export interface WritingArticles {
	title: string;
	content: string;
	category: string;
}

type Category = 'question' | 'discussion' | 'total';
type Sort = 'latest' | 'views';

export const postWritingArticle = (article: WritingArticles) => {
	const accessToken = localStorage.getItem('accessToken');
	return axios.post(`${HOME_URL}/api/articles`, article, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};

export interface PopularArticles {
	articles: ArticleType[];
	hastNext: boolean;
}

export const getPopularArticles = async () => {
	const result = await axios.get<PopularArticles>(
		`${HOME_URL}/api/articles?category=all&sort=views&pageSize=10`,
	);
	return result.data;
};

export const getDetailArticle = async (id: string) => {
	const accessToken = localStorage.getItem('accessToken');
	const { data } = await axios.get<ArticleType>(`${HOME_URL}/api/articles/${id}`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
	return data;
};

export const getAllArticle = async ({
	category,
	sort,
	cursorId = '',
	cursorViews = '',
}: {
	category: string;
	sort: string;
	cursorId?: string;
	cursorViews?: string;
}) => {
	const currentSort = sort === '최신순' ? 'latest' : 'views';
	const { data } = await axios.get<AllArticleResponse>(
		`${HOME_URL}/api/articles?category=${category}&sort=${currentSort}&cursorId=${cursorId}&cursorViews=${cursorViews}&pageSize=5`,
	);

	return {
		articles: data.articles,
		hasNext: data.hasNext,
		cursorId: data.articles[data.articles.length - 1]?.id,
		cursorViews: data.articles[data.articles.length - 1]?.views,
	};
};

export const postArticle = (article: { id: string; title: string; content: string }) => {
	const accessToken = localStorage.getItem('accessToken');

	return axios.post<{ id: number; category: string }>(
		`${HOME_URL}/api/articles/${article.id}`,
		{ title: article.title, content: article.content },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
};

export const putArticle = (article: { id: string; title: string; content: string }) => {
	const accessToken = localStorage.getItem('accessToken');
	return axios.put<{ id: number; category: string }>(
		`${HOME_URL}/api/articles/${article.id}`,
		{ title: article.title, content: article.content },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
};

export const deleteArticle = (id: string) => {
	const accessToken = localStorage.getItem('accessToken');
	return axios.delete<never, unknown, unknown>(`${HOME_URL}/api/articles/${id}`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
