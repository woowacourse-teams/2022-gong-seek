import { CommentType } from '@/types/commentResponse';
import axios from 'axios';

export const postComments = ({ content }: { content: string }) => {
	const accessToken = localStorage.getItem('accessToken');
	return axios.post(
		'http://192.168.0.155:8080/api/articles/:articleId/comments',
		{ content },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
};

export const getComments = async () => {
	const accessToken = localStorage.getItem('accessToken');

	const response = await axios.get<{ comments: CommentType[] }>(
		'http://192.168.0.155:8080/api/articles/:articleId/comments',
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);

	return response.data.comments;
};

export const putComments = ({ content }: { content: string }) => {
	const accessToken = localStorage.getItem('accessToken');

	return axios.put(
		'http://192.168.0.155:8080/api/articles/:articleId/comments/:commentId',
		{ content },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
};

export const deleteComments = () => {
	const accessToken = localStorage.getItem('accessToken');

	return axios.delete('http://192.168.0.155:8080/api/articles/:articleId/comments/:commentId', {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
