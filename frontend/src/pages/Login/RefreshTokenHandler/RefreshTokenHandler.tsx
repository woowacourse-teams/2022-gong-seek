import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import { getAccessTokenByRefreshToken } from '@/api/login';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useSnackBar from '@/hooks/useSnackBar';
import { useEffect } from '@storybook/addons';

const RefreshTokenHandler = () => {
	const { data, isSuccess, isError, error } = useQuery<
		{ accessToken: string },
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('getBack-accessToken', getAccessTokenByRefreshToken);
	const { showSnackBar } = useSnackBar();

	useEffect(() => {
		localStorage.removeItem('accessToken');
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
			localStorage.setItem('accessToken', data.accessToken);
			showSnackBar('재로그인 되었습니다');
			location.href = '/';
		}
	}, [isSuccess]);

	return <div>재로그인중입니다</div>;
};

export default RefreshTokenHandler;
