import axios, { AxiosResponse } from 'axios';

import { ACCESSTOKEN_KEY } from '@/constants';
import { HOME_URL } from '@/constants/apiUrl';
import { UserArticlesResponse } from '@/types/articleResponse';
import { Author } from '@/types/author';
import { UserCommentResponse } from '@/types/commentResponse';

export const getUserInfo = async () => {
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);
	const result = await axios.get<Author>(`${HOME_URL}/api/members/me`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
	return result.data;
};

export const getUserArticles = async () => {
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);
	const result = await axios.get<UserArticlesResponse>(`${HOME_URL}/api/members/me/articles`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
	return result.data;
};

export const getUserComments = async () => {
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);
	const result = await axios.get<UserCommentResponse>(`${HOME_URL}/api/members/me/comments`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
	return result.data;
};

export const editUserInfo = ({ name }: { name: string }) => {
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);

	return axios.patch<{ name: string }, AxiosResponse<{ name: string }>>(
		`${HOME_URL}/api/members/me`,
		{ name },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
};
