import axios from 'axios';

import { HOME_URL } from '@/constants/apiUrl';
import { postTempArticleProps } from '@/hooks/tempArticle/usePostTempArticle';
import { TempArticleDetailResponse, TempArticleResponse } from '@/types/articleResponse';

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

export const getTempDetailArticle = ({ id }: { id: number | '' }) => {
	const accessToken = localStorage.getItem('accessToken');
	return axios.get<TempArticleDetailResponse>(`${HOME_URL}/api/temp-articles/${id}`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};

export const postTempArticle = ({
	title,
	content,
	category,
	tag,
	isAnonymous,
	tempArticleId,
}: postTempArticleProps) => {
	const accessToken = localStorage.getItem('accessToken');

	return axios.post<{ id: number }>(
		`${HOME_URL}/api/temp-articles`,
		{ title, content, category, tag, isAnonymous, tempArticleId },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
};

export const deleteArticleItem = ({ tempArticleId }: { tempArticleId: number }) => {
	const accessToken = localStorage.getItem('accessToken');
	return axios.delete<never, unknown, unknown>(`${HOME_URL}/api/temp-articles/${tempArticleId}`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
