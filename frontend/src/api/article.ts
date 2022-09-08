import axios from 'axios';

import { HOME_URL } from '@/constants/url';
import { AllArticleResponse, ArticleType } from '@/types/articleResponse';
import { convertSort } from '@/utils/converter';

export interface WritingArticles {
	title: string;
	content: string;
	category: string;
}

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
	const accessToken = localStorage.getItem('accessToken');

	const result = await axios.get<PopularArticles>(
		`${HOME_URL}/api/articles?category=all&sort=views&size=10`,
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
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
	cursorId,
	cursorViews,
	cursorLikes,
}: {
	category: string;
	sort: '좋아요순' | '조회순' | '최신순';
	cursorId: string;
	cursorViews: string;
	cursorLikes: string;
}) => {
	const accessToken = localStorage.getItem('accessToken');

	if (sort === '좋아요순') {
		const data = await getAllArticlesByLikes({ category, cursorId, cursorLikes, accessToken });
		return data;
	}

	const data = getAllArticleByViewsOrLatest({ category, sort, cursorId, cursorViews, accessToken });
	return data;
};

export const getAllArticleByViewsOrLatest = async ({
	category,
	sort,
	cursorId,
	cursorViews,
	accessToken,
}: {
	category: string;
	sort: '좋아요순' | '조회순' | '최신순';
	cursorId: string;
	cursorViews: string;
	accessToken: string | null;
}) => {
	const currentSort = convertSort(sort);

	const { data } = await axios.get<AllArticleResponse>(
		`${HOME_URL}/api/articles?category=${category}&sort=${currentSort}&cursorId=${cursorId}&cursorViews=${cursorViews}&size=6`,
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);

	return {
		articles: data.articles,
		hasNext: data.hasNext,
		cursorId: String(data.articles[data.articles.length - 1]?.id),
		cursorViews: String(data.articles[data.articles.length - 1]?.views),
	};
};

export const getAllArticlesByLikes = async ({
	category,
	cursorId,
	cursorLikes,
	accessToken,
}: {
	category: string;
	cursorId: string;
	cursorLikes: string;
	accessToken: string | null;
}) => {
	const { data } = await axios.get<AllArticleResponse>(
		`${HOME_URL}/api/articles/likes?category=${category}&cursorId=${cursorId}&cursorLikes=${cursorLikes}&size=6`,
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);

	return {
		articles: data.articles,
		hasNext: data.hasNext,
		cursorId: String(data.articles[data.articles.length - 1]?.id),
		cursorLikes: String(data.articles[data.articles.length - 1]?.likeCount),
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

export const putArticle = (article: {
	id: string;
	title: string;
	content: string;
	tag: string[];
}) => {
	const accessToken = localStorage.getItem('accessToken');
	return axios.put<{ id: number; category: string }>(
		`${HOME_URL}/api/articles/${article.id}`,
		{ title: article.title, content: article.content, tag: article.tag },
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
