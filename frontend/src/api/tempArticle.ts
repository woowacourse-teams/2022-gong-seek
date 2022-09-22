import axios from 'axios';

import { ACCESSTOKEN_KEY } from '@/constants';
import { HOME_URL } from '@/constants/apiUrl';
import { postTempArticleProps } from '@/hooks/tempArticle/usePostTempArticle';
import { TempArticleDetailResponse, TempArticleResponse } from '@/types/articleResponse';

export const getTempArticles = async () => {
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);
	const result = await axios.get<TempArticleResponse>(`${HOME_URL}/api/temp-articles`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
	return result.data;
};

export const getTempDetailArticle = ({ id }: { id: number | '' }) => {
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);
	return axios.get<TempArticleDetailResponse>(`${HOME_URL}/api/temp-articles/${id}`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};

export const postTempArticle = ({ ...props }: postTempArticleProps) => {
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);

	return axios.post<{ id: number }>(
		`${HOME_URL}/api/temp-articles`,
		{ ...props },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
};

export const deleteArticleItem = ({ tempArticleId }: { tempArticleId: number }) => {
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);
	return axios.delete<never, unknown, unknown>(`${HOME_URL}/api/temp-articles/${tempArticleId}`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
