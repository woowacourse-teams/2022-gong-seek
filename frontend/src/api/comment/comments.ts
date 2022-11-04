import { TotalCommentResponseType } from '@/api/comment/commentType';
import { generateAxiosInstanceWithAccessToken } from '@/utils/generateAxiosInstance';

interface postCommentsProps {
	id: string;
	content: string;
	isAnonymous: boolean;
}

export const postComments = ({ id, content, isAnonymous }: postCommentsProps) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();

	return axiosInstance.post(`/api/articles/${id}/comments`, { content, isAnonymous });
};

export const getComments = async (id: string) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();

	const { data } = await axiosInstance.get<TotalCommentResponseType>(
		`/api/articles/${id}/comments`,
	);
	return data;
};

interface putCommentsProps {
	content: string;
	commentId: string;
}

export const putComments = ({ content, commentId }: putCommentsProps) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	return axiosInstance.put(`/api/articles/comments/${commentId}`, { content });
};

export const deleteComments = ({ commentId }: { commentId: string }) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	return axiosInstance.delete(`/api/articles/comments/${commentId}`);
};
