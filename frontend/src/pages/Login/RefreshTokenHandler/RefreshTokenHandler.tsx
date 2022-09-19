import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { getAccessTokenByRefreshToken } from '@/api/login';
import Loading from '@/components/common/Loading/Loading';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { URL } from '@/constants/url';
import useSnackBar from '@/hooks/common/useSnackBar';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const RefreshTokenHandler = () => {
	const { data, isSuccess, error } = useQuery<
		{ accessToken: string },
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('getBack-accessToken', getAccessTokenByRefreshToken, {
		retry: 1,
	});

	const { showSnackBar } = useSnackBar();
	const navigate = useNavigate();

	useThrowCustomError(error);

	useEffect(() => {
		if (isSuccess) {
			localStorage.setItem('accessToken', data.accessToken);
			navigate(URL.HOME);
			showSnackBar('재로그인 되었습니다');
		}
	}, [isSuccess]);

	return <Loading />;
};

export default RefreshTokenHandler;
