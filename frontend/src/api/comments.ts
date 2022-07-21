import { HOME_URL } from '@/constants/url';
import { CommentType } from '@/types/commentResponse';
import axios from 'axios';

export const postComments = ({ content, id }: { content: string; id: string }) => {
	const accessToken = localStorage.getItem('accessToken');
	return axios.post(
		`${HOME_URL}/api/articles/${id}/comments`,
		{ content },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
};

export const getComments = async (id: string) => {
	const accessToken = localStorage.getItem('accessToken');

	const { data } = await axios.get<{ comments: CommentType[] }>(
		`${HOME_URL}/api/articles/${id}/comments`,
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
	return data;
};

export const putComments = ({ content, commentId }: { content: string; commentId: string }) => {
	const accessToken = localStorage.getItem('accessToken');

	return axios.put(
		`${HOME_URL}/api/articles/comments/${commentId}`,
		{ content },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
};

export const deleteComments = ({ commentId }: { commentId: string }) => {
	const accessToken = localStorage.getItem('accessToken');

	return axios.delete(`${HOME_URL}/api/articles/comments/${commentId}`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
