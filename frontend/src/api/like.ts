import axios from 'axios';

import { HOME_URL } from '@/constants/url';

export const postAddLikeArticle = (articleId: string) => {
	const accessToken = localStorage.getItem('accessToken');
	return axios.post(`${HOME_URL}/api/articles/${articleId}/like`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};

export const deleteLikeArticle = (articleId: string) => {
	const accessToken = localStorage.getItem('accessToken');
	return axios.delete(`${HOME_URL}/api/articles/${articleId}/unlike`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
