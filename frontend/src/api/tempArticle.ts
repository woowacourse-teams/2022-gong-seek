import axios from 'axios';

import { HOME_URL } from '@/constants/apiUrl';
import { TempArticleResponse } from '@/types/articleResponse';

export const getTempArticles = async () => {
	const accessToken = localStorage.getItem('accessToken');
	const result = await axios.get<TempArticleResponse>(`${HOME_URL}/api/temp-articles`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
	return result.data;
};
