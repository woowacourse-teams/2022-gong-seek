import { AxiosError, AxiosResponse } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useNavigate, useSearchParams } from 'react-router-dom';

import { postLogin } from '@/api/login';
import useSnackBar from '@/hooks/useSnackBar';

const LoginController = () => {
	const [searchParams] = useSearchParams();
	const { showSnackBar } = useSnackBar();
	const navigate = useNavigate();

	const code = searchParams.get('code');
	if (code === null) {
		showSnackBar('깃허브 로그인에 동의해주세요.');
		navigate('/');
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
			localStorage.setItem('gongseekAccessToken', data.data.accessToken);
			window.location.href = '/';
		}
		if (isError) {
			window.location.href = '/';
		}
	}, [isSuccess, isError]);

	return <div>로그인 중입니다...</div>;
};

export default LoginController;
