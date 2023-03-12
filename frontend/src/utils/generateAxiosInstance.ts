import axios from 'axios';

import { ACCESSTOKEN_KEY } from '@/constants';
import { HOME_URL } from '@/constants/apiUrl';

export const generateAxiosInstanceWithAccessToken = () => {
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);
	return axios.create({
		baseURL: HOME_URL,
		headers: {
			Authorization: `Bearer ${accessToken}`,
			'content-Type': 'Application/json',
		},
	});
};
