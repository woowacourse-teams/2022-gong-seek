import axios from 'axios';

import { ACCESSTOKEN_KEY } from '@/constants';
import { HOME_URL } from '@/constants/apiUrl';

export const postAddLikeArticle = (articleId: string) => {
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);
	return axios.post(`${HOME_URL}/api/articles/${articleId}/like`, null, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};

export const deleteLikeArticle = (articleId: string) => {
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);
	return axios.delete(`${HOME_URL}/api/articles/${articleId}/like`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
