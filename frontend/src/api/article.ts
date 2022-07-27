import axios from 'axios';

import { HOME_URL } from '@/constants/url';
import { ArticleType, CommonArticleType } from '@/types/articleResponse';

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
		`${HOME_URL}/api/articles?category=total&sort=views`,
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

interface ArticlesType {
	articles: CommonArticleType[];
}

// export const getAllArticle = async (category: string, sort: string, pageParam = 0) => {
// 	const { data } = await axios.get<ArticlesType>(
// 		`${HOME_URL}/api/articles?category=${category}&sort=${sort}&page=${pageParam}&size=5`,
// 	);
// 	console.log(data);

// 	return { articles: data.articles, hasNext: data.hasNext, pageParam: pageParam + 1 };
// };

export const getAllArticle = async (category: string, sort: string) => {
	let tempSort = 'latest';
	if (sort === '조회순') {
		tempSort = 'views';
	}
	const { data } = await axios.get<ArticlesType>(
		`${HOME_URL}/api/articles?category=${category}&sort=${tempSort}`,
	);
	return data;
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
