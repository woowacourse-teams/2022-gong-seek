import axios from 'axios';

import { HOME_URL } from '@/constants/apiUrl';
import { postTempArticleProps } from '@/hooks/tempArticle/usePostTempArticle';
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

export const postTempArticle = async ({
	title,
	content,
	category,
	tags,
	isAnonymous,
	tempArticleId,
}: postTempArticleProps) => {
	const accessToken = localStorage.getItem('accessToken');

	return axios.post<{ id: number }>(
		`${HOME_URL}/api/temp-articles`,
		{ title, content, category, tags, isAnonymous, tempArticleId },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
};
