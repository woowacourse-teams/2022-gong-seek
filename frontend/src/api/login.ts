import axios from 'axios';

export const getGithubURL = async () => {
	const response = await axios.get<{ url: string }>('/api/auth/github');

	return response.data.url;
};

export const postLogin = (code: string) =>
	axios.post<{ accesstoken: string }>('api/auth/token', { code });
