import { AxiosResponse } from 'axios';

import { AuthorType, MyPageUserArticleResponseType } from '@/api/article/articleType';
import { UserCommentResponse } from '@/types/commentResponse';
import { generateAxiosInstanceWithAccessToken } from '@/utils/generateAxiosInstance';

export const getUserInfo = async () => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	const { data } = await axiosInstance.get<AuthorType>('/api/members/me');
	return data;
};

export const getUserArticles = async () => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	const { data } = await axiosInstance.get<MyPageUserArticleResponseType>(
		'/api/members/me/articles',
	);
	return data;
};

export const getUserComments = async () => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	const { data } = await axiosInstance.get<UserCommentResponse>('/api/members/me/comments');
	return data;
};

export const editUserInfo = ({ name }: { name: string }) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	return axiosInstance.patch<{ name: string }, AxiosResponse<{ name: string }>>('/api/members/me', {
		name,
	});
};
