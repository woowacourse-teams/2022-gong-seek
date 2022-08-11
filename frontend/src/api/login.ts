import axios from 'axios';

import { HOME_URL } from '@/constants/url';

export const getGithubURL = async () => {
	const response = await axios.get<{ url: string }>(`${HOME_URL}/api/auth/github`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
		},
	});

	return response.data.url;
};

export const postLogin = (code: string) =>
	axios.post<{ accessToken: string }>(
		`${HOME_URL}/api/auth/login`,
		{ code },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
			},
		},
	);
