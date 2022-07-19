import { HOME_URL } from '@/constants/url';
import { ArticleType } from '@/types/articleResponse';
import axios from 'axios';

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

interface PopularArticles {
	articles: ArticleType[];
	hastNext: boolean;
}

export const getPopularArticles = async () => {
	const result = await axios.get<PopularArticles>(
		`${HOME_URL}/api/articles?category=total&sort=views&page=1&size=10`,
	);
	console.log('article', result);
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
	console.log('article', data);
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
	return axios.delete<unknown, unknown, unknown>(`${HOME_URL}/api/articles/${id}`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
