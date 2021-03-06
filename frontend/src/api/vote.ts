import axios from 'axios';

import { HOME_URL } from '@/constants/url';

export interface VoteItems {
	option: string;
	count: number;
}

export const getVoteItems = async (articleId: string) => {
	const accessToken = localStorage.getItem('accessToken');

	const { data } = await axios.get<VoteItems[]>(`${HOME_URL}/articles/${articleId}/votes`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});

	return data;
};

export const registerVoteItems = ({
	articleId,
	options,
}: {
	articleId: string;
	options: string[];
}) => {
	const accessToken = localStorage.getItem('accessToken');

	return axios.post<{ articleId: string }>(
		`${HOME_URL}/api/articles/${articleId}/votes`,
		{ options },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
};

export const checkVoteItems = ({ articleId, voteId }: { articleId: string; voteId: string }) => {
	const accessToken = localStorage.getItem('accessToken');

	return axios.post(`${HOME_URL}/api/articles/${articleId}/votes/${voteId}`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
