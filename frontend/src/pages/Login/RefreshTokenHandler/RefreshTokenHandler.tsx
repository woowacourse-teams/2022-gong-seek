import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

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
	>('getBack-accessToken', getAccessTokenByRefreshToken);

	const { showSnackBar } = useSnackBar();

	useThrowCustomError(error);

	useEffect(() => {
		if (isSuccess) {
			localStorage.setItem('accessToken', data.accessToken);
			location.href = URL.HOME;
			showSnackBar('재로그인 되었습니다');
		}
	}, [isSuccess]);

	return <Loading />;
};

export default RefreshTokenHandler;
