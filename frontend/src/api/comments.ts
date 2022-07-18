import axios from 'axios';

export interface CommentsResponse {
	id: number;
	content: string;
	authorName: string;
	authorAvartarUrl: string;
	createdAt: string;
	isAuthor: boolean;
}

export const postComments = ({ articleId, content }: { articleId: string; content: string }) => {
	const accessToken = localStorage.getItem('accessToken');
	return axios.post(
		`http://192.168.0.155:8080/api/articles/${articleId}/comments`,
		{ content },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
};

export const getComments = async ({ articleId }: { articleId: string }) => {
	const accessToken = localStorage.getItem('accessToken');

	const response = await axios.get<{ comments: CommentsResponse[] }>(
		`http://192.168.0.155:8080/api/articles/${articleId}/comments`,
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);

	console.log(response);

	return response.data.comments;
};

export const putComments = ({
	content,
	articleId,
	commentId,
}: {
	content: string;
	articleId: string;
	commentId: string;
}) => {
	const accessToken = localStorage.getItem('accessToken');

	return axios.put(
		`http://192.168.0.155:8080/api/articles/${articleId}/comments/${commentId}`,
		{ content },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
};

export const deleteComments = ({
	articleId,
	commentId,
}: {
	articleId: string;
	commentId: string;
}) => {
	const accessToken = localStorage.getItem('accessToken');

	return axios.delete(`http://192.168.0.155:8080/api/articles/${articleId}/comments/${commentId}`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
