import axios from 'axios';

export interface WritingArticles {
	title: string;
	content: string;
	category: string;
}

export const postWritingArticle = (article: WritingArticles) => {
	const accessToken = localStorage.getItem('accessToken');
	return axios.post('http://192.168.0.155:8080/api/articles', article, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
