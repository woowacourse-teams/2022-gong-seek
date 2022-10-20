import axios from 'axios';

import { ACCESSTOKEN_KEY } from '@/constants';
import { HOME_URL } from '@/constants/apiUrl';

export interface VoteItems {
	id: number;
	content: string;
	amount: number;
}

export interface TVote {
	articleId: string;
	voteItems: VoteItems[];
	voteItemId: number | null;
	isExpired: boolean;
}

export const getVoteItems = async (articleId: string) => {
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);

	const { data } = await axios.get<TVote>(`${HOME_URL}/api/articles/${articleId}/votes`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});

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
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);

	return axios.post<{ articleId: string }>(
		`${HOME_URL}/api/articles/${articleId}/votes`,
		{ items, expiryDate },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
};

export const checkVoteItems = ({
	articleId,
	voteItemId,
}: {
	articleId: string;
	voteItemId: string;
}) => {
	const accessToken = localStorage.getItem(ACCESSTOKEN_KEY);

	return axios.post(
		`${HOME_URL}/api/articles/${articleId}/votes/do`,
		{ voteItemId },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
};
