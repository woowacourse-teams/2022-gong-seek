import axios from 'axios';

export interface VoteItems {
	option: string;
	count: number;
}

export const getVoteItems = async () => {
	const accessToken = localStorage.getItem('accessToken');

	const { data } = await axios.get<VoteItems[]>('http://192.168.0.155:8080/api/articles/1/votes', {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});

	return data;
};

export const registerVoteItems = () => {
	const accessToken = localStorage.getItem('accessToken');

	return axios.post<{ articleId: string }>('http://192.168.0.155:8080/api/articles/1/votes', {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};

export const checkVoteItems = (voteId: string) => {
	const accessToken = localStorage.getItem('accessToken');

	return axios.post(`http://192.168.0.155:8080/api/articles/1/votes/${voteId}`, {
		headers: {
			'Access-Control-Allow-Origin': '*',
			Authorization: `Bearer ${accessToken}`,
		},
	});
};
