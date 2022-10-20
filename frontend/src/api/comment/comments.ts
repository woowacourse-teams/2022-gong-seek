import { CommentType } from '@/types/commentResponse';
import { generateAxiosInstanceWithAccessToken } from '@/utils/generateAxiosInstance';

export const postComments = ({
	content,
	id,
	isAnonymous,
}: {
	content: string;
	id: string;
	isAnonymous: boolean;
}) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();

	return axiosInstance.post(`/api/articles/${id}/comments`, { content, isAnonymous });
};

export const getComments = async (id: string) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();

	const { data } = await axiosInstance.get<{ comments: CommentType[] }>(
		`/api/articles/${id}/comments`,
	);
	return data;
};

export const putComments = ({ content, commentId }: { content: string; commentId: string }) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	return axiosInstance.put(`/api/articles/comments/${commentId}`, { content });
};

export const deleteComments = ({ commentId }: { commentId: string }) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	return axiosInstance.delete(`/api/articles/comments/${commentId}`);
};
