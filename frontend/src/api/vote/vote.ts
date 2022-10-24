import { CheckVoteRequestType, CreateVoteRequestType, VoteResponseType } from '@/api/vote/voteType';
import { generateAxiosInstanceWithAccessToken } from '@/utils/generateAxiosInstance';

export const getVoteItems = async (articleId: string) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();

	const { data } = await axiosInstance.get<VoteResponseType>(`/api/articles/${articleId}/votes`);

	return data;
};

export const registerVoteItems = ({ articleId, items, expiryDate }: CreateVoteRequestType) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();

	return axiosInstance.post<{ articleId: string }>(`/api/articles/${articleId}/votes`, {
		items,
		expiryDate,
	});
};

export const checkVoteItems = ({ articleId, voteItemId }: CheckVoteRequestType) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();

	return axiosInstance.post(`/api/articles/${articleId}/votes/do`, { voteItemId });
};
