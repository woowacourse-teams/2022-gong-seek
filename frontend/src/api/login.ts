import axios from 'axios';

export const getGithubURL = async () => {
	const response = await axios.get<{ url: string }>('http://192.168.0.155:8080/api/auth/github', {
		headers: {
			'Access-Control-Allow-Origin': '*',
		},
	});

	return response.data.url;
};

export const postLogin = (code: string) =>
	axios.post<{ accesstoken: string }>(
		'http://192.168.0.155:8080/api/auth/token',
		{ code },
		{
			headers: {
				'Access-Control-Allow-Origin': '*',
			},
		},
	);
