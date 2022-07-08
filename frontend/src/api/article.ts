import axios from 'axios';

export const postWritingArticle = (article: {
	title: string;
	content: string;
	category: string;
}) => {
	const accessToken = localStorage.getItem('accessToken');
	console.log(accessToken);
	return axios.post('http://192.168.0.155:8080/api/articles', article, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
