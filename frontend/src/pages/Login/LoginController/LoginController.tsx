import { AxiosError, AxiosResponse } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useNavigate, useSearchParams } from 'react-router-dom';

import { postLogin } from '@/api/login';
import { ACCESSTOKEN_KEY } from '@/constants';
import { URL } from '@/constants/url';
import useSnackBar from '@/hooks/common/useSnackBar';
import LoginLoading from '@/pages/Login/LoginLoading/LoginLoading';

const LoginController = () => {
	const [searchParams] = useSearchParams();
	const { showSnackBar } = useSnackBar();
	const navigate = useNavigate();

	const code = searchParams.get('code');

	const { data, isError, isSuccess, error, mutate } = useMutation<
		AxiosResponse<{ accessToken: string }>,
		AxiosError<{ errorCode: string; message: string }>,
		string
	>(postLogin);

	useEffect(() => {
		if (code === null) {
			showSnackBar('깃허브 로그인에 동의해주세요.');
			navigate(URL.HOME);
			return;
		}
		mutate(code);
	}, [code]);

	useEffect(() => {
		if (isSuccess) {
			localStorage.setItem(ACCESSTOKEN_KEY, data.data.accessToken);
			window.location.href = URL.HOME;
		}
		if (isError) {
			window.location.href = URL.HOME;
		}
	}, [isSuccess, isError]);

	return <LoginLoading />;
};

export default LoginController;
