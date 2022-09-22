import axios from 'axios';

import { ACCESSTOKEN_KEY } from '@/constants';
import { HOME_URL } from '@/constants/apiUrl';

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
			withCredentials: true,
		},
	);

export const getAccessTokenByRefreshToken = async () => {
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);
	const response = await axios.get<{ accessToken: string }>(`${HOME_URL}/api/auth/refresh`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			'Access-Control-Allow-Credentials': true,
			Authorization: `Bearer ${accessToken}`,
		},
		withCredentials: true,
	});
	return response.data;
};
