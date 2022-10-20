import axios from 'axios';

import { ACCESSTOKEN_KEY } from '@/constants';
import { HOME_URL } from '@/constants/apiUrl';

export const postImageUrlConverter = async (formData: FormData) => {
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);

	const response = await axios.post(`${HOME_URL}/api/images`, formData, {
		headers: {
			'Content-Type': 'multipart/form-data',
			Authorization: `Bearer ${accessToken}`,
		},
	});

	return response.data.imageUrl;
};
