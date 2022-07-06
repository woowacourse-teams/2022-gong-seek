import axios from 'axios';

export const postWritingArticle = (article: {
	title: string;
	content: string;
	category: string;
}) => {
	const accessToken = localStorage.getItem('accessToken');
	return axios.post('/api/articles', {
		header: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
		body: JSON.stringify({
			article,
		}),
	});
};
