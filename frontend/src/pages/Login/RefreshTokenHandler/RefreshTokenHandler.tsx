import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getAccessTokenByRefreshToken } from '@/api/login';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { ACCESSTOKEN_KEY } from '@/constants/index';
import { URL } from '@/constants/url';
import useSnackBar from '@/hooks/common/useSnackBar';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import LoginLoading from '@/pages/Login/LoginLoading/LoginLoading';

const RefreshTokenHandler = () => {
	const { data, isSuccess, isError, error } = useQuery<
		{ accessToken: string },
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('getBack-accessToken', getAccessTokenByRefreshToken, {
		retry: 1,
	});

	const { showSnackBar } = useSnackBar();

	useThrowCustomError(isError, error);

	useEffect(() => {
		if (isSuccess) {
			localStorage.setItem(ACCESSTOKEN_KEY, data.accessToken);
			location.href = URL.HOME;
			showSnackBar('재로그인 되었습니다');
		}
	}, [isSuccess]);

	return <LoginLoading />;
};

export default RefreshTokenHandler;
