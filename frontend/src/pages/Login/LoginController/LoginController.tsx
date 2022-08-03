import { AxiosError, AxiosResponse } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useSearchParams } from 'react-router-dom';

import { postLogin } from '@/api/login';

const LoginController = () => {
	const [searchParams] = useSearchParams();

	const code = searchParams.get('code');
	if (code === null) {
		throw new Error('코드가 존재하지 않습니다.');
	}
	const { data, isError, isSuccess, error, mutate } = useMutation<
		AxiosResponse<{ accessToken: string }>,
		AxiosError<{ errorCode: string; message: string }>,
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
			window.location.href = '/';
		}
	}, [isSuccess, isError]);

	return <div>로그인 중입니다...</div>;
};

export default LoginController;
