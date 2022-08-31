import axios from 'axios';

import { IMAGE_URL } from '@/constants/url';

export const postImageUrlConverter = async (formData: FormData) => {
	const accessToken = localStorage.getItem('gongseekAccessToken');

	const response = await axios.post(`${IMAGE_URL}/api/images/upload`, formData, {
		headers: {
			'Content-Type': 'multipart/form-data',
			Authorization: `Bearer ${accessToken}`,
		},
	});

	return response.data.imageUrl;
};
