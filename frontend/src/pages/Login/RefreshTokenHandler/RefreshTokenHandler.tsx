import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getAccessTokenByRefreshToken } from '@/api/login';
import Loading from '@/components/common/Loading/Loading';
import RefreshErrorBoundary from '@/components/helper/RefreshErrorBoundary';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { URL } from '@/constants/url';
import useSnackBar from '@/hooks/common/useSnackBar';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

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
			showSnackBar('재로그인 되었습니다');
			localStorage.setItem('accessToken', data.accessToken);
			window.location.href = URL.HOME;
		}
	}, [isSuccess]);

	return (
		<RefreshErrorBoundary>
			<Loading />
		</RefreshErrorBoundary>
	);
};

export default RefreshTokenHandler;
