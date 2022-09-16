import axios from 'axios';

import { HOME_URL } from '@/constants/apiUrl';

export const getAllHashTag = async () => {
	const accessToken = localStorage.getItem('gongseekAccessToken');
	const data = await axios.get<{ tag: string[] }>(`${HOME_URL}/api/tags`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
	return data.data;
};
