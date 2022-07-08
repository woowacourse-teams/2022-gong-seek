import { postLogin } from '@/api/login';
import { AxiosError, AxiosResponse } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useNavigate, useSearchParams } from 'react-router-dom';

const LoginController = () => {
	const navigate = useNavigate();
	const [searchParams] = useSearchParams();

	const code = searchParams.get('code');
	if (code === null) {
		throw new Error('코드가 존재하지 않습니다.');
	}
	const { data, isError, isSuccess, error, mutate } = useMutation<
		AxiosResponse<{ accessToken: string }>,
		AxiosError,
		string
	>(postLogin);

	useEffect(() => {
		mutate(code);
	}, []);

	useEffect(() => {
		if (isSuccess) {
			localStorage.setItem('accessToken', data.data.accessToken);
			window.location.href = '/';
		}
		if (isError) {
			console.log(error.message);
			window.location.href = '/';
		}
	}, [isSuccess, isError]);

	return <div>로그인 중입니다...</div>;
};

export default LoginController;
