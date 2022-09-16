import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getAccessTokenByRefreshToken } from '@/api/login';
import CustomError from '@/components/helper/CustomError';
import { ACCESSTOKEN_KEY } from '@/constants';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { URL } from '@/constants/url';
import useSnackBar from '@/hooks/common/useSnackBar';

const RefreshTokenHandler = () => {
	const { data, isSuccess, isError, error } = useQuery<
		{ accessToken: string },
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('getBack-accessToken', getAccessTokenByRefreshToken);

	const { showSnackBar } = useSnackBar();

	useEffect(() => {
		localStorage.removeItem(ACCESSTOKEN_KEY);
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
			localStorage.setItem(ACCESSTOKEN_KEY, data.accessToken);
			location.href = URL.HOME;
			showSnackBar('재로그인 되었습니다');
		}
	}, [isSuccess]);

	return <div>재로그인중입니다</div>;
};

export default RefreshTokenHandler;
