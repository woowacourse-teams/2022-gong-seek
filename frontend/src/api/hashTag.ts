import axios from 'axios';

import { HOME_URL } from '@/constants/url';

export const getAllHashTag = () => {
	const accessToken = localStorage.getItem('accessToken');
	return axios.get<{ tags: string[] }>(`${HOME_URL}/api/tags`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
