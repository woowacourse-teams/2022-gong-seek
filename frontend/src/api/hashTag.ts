import axios from 'axios';

import { HOME_URL } from '@/constants/url';

export const getAllHashTag = async () => {
	const accessToken = localStorage.getItem('accessToken');
	const data = await axios.get<{ tag: string[] }>(`${HOME_URL}/api/tags`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
	return data.data;
};
