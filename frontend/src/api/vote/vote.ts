import { generateAxiosInstanceWithAccessToken } from '@/utils/generateAxiosInstance';

export interface VoteItems {
	id: number;
	content: string;
	amount: number;
}

export interface TVote {
	articleId: string;
	voteItems: VoteItems[];
	votedItemId: number | null;
	isExpired: boolean;
}

export const getVoteItems = async (articleId: string) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();

	const { data } = await axiosInstance.get<TVote>(`/api/articles/${articleId}/votes`);

	return data;
};

export const registerVoteItems = ({
	articleId,
	items,
	expiryDate,
}: {
	articleId: string;
	items: string[];
	expiryDate: string;
}) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();

	return axiosInstance.post<{ articleId: string }>(`/api/articles/${articleId}/votes`, {
		items,
		expiryDate,
	});
};

export const checkVoteItems = ({
	articleId,
	votedItemId,
}: {
	articleId: string;
	votedItemId: string;
}) => {
	const axiosInstance = generateAxiosInstanceWithAccessToken();

	return axiosInstance.post(`/api/articles/${articleId}/votes/do`, { votedItemId });
};
