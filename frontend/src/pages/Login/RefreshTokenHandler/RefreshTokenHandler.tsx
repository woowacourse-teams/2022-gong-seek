import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useRecoilState } from 'recoil';

import { getAccessTokenByRefreshToken } from '@/api/login';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { URL } from '@/constants/url';
import useSnackBar from '@/hooks/common/useSnackBar';
import { errorPortalState } from '@/store/errorPortalState';

const RefreshTokenHandler = () => {
	const { data, isSuccess, isError, error } = useQuery<
		{ accessToken: string },
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('getBack-accessToken', getAccessTokenByRefreshToken);

	const [errorPortal, setErrorPortal] = useRecoilState(errorPortalState);
	const { showSnackBar } = useSnackBar();

	useEffect(() => {
		localStorage.removeItem('accessToken');
	}, []);

	useEffect(() => {
		if (isError) {
			if (!error.response?.data?.errorCode) {
				setErrorPortal({ isOpen: true });
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response?.data.errorCode],
			);
		}
	}, [isError]);

	useEffect(() => {
		if (isSuccess) {
			localStorage.setItem('accessToken', data.accessToken);
			location.href = URL.HOME;
			showSnackBar('재로그인 되었습니다');
		}
	}, [isSuccess]);

	return <div>재로그인중입니다</div>;
};

export default RefreshTokenHandler;
