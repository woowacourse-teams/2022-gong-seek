import axios from 'axios';

export interface VoteItems {
	option: string;
	count: number;
}

export const getVoteItems = async (articleId: string) => {
	const accessToken = localStorage.getItem('accessToken');

	const { data } = await axios.get<VoteItems[]>(
		`http://192.168.0.155:8080/api/articles/${articleId}/votes`,
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
				Authorization: `Bearer ${accessToken}`,
			},
		},
	);
	console.log(data);

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
		`http://192.168.0.155:8080/api/articles/${articleId}/votes`,
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

	return axios.post(`http://192.168.0.155:8080/api/articles/${articleId}/votes/${voteId}`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
