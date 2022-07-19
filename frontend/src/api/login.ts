import { HOME_URL } from '@/constants/url';
import axios from 'axios';

export const getGithubURL = async () => {
	console.log(HOME_URL);
	const response = await axios.get<{ url: string }>(`${HOME_URL}/api/auth/github`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
		},
	});

	return response.data.url;
};

export const postLogin = (code: string) =>
	axios.post<{ accessToken: string }>(
		`${HOME_URL}/api/auth/token`,
		{ code },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
			},
		},
	);
