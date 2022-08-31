import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getAccessTokenByRefreshToken } from '@/api/login';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useSnackBar from '@/hooks/useSnackBar';

const RefreshTokenHandler = () => {
	const { data, isSuccess, isError, error } = useQuery<
		{ accessToken: string },
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('getBack-accessToken', getAccessTokenByRefreshToken);

	const { showSnackBar } = useSnackBar();

	useEffect(() => {
		localStorage.removeItem('gongseekAccessToken');
	}, []);

	useEffect(() => {
		if (isError) {
			if (!error.response) {
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response.data.errorCode],
			);
		}
	}, [isError]);

	useEffect(() => {
		if (isSuccess) {
			localStorage.setItem('gongseekAccessToken', data.accessToken);
			location.href = '/';
			showSnackBar('재로그인 되었습니다');
		}
	}, [isSuccess]);

	return <div>재로그인중입니다</div>;
};

export default RefreshTokenHandler;
