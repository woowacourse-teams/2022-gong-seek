import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';
import { useRecoilState } from 'recoil';

import { getUserInfo } from '@/api/myPage';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { errorPortalState } from '@/store/errorPortalState';
import { Author } from '@/types/author';

const useGetUserInfo = () => {
	const { data, isSuccess, isError, isLoading, isIdle, error } = useQuery<
		Author,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('user-info', getUserInfo, { retry: 1, refetchOnWindowFocus: false });
	const [errorPortal, setErrorPortal] = useRecoilState(errorPortalState);

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

	return {
		data,
		isSuccess,
		isLoading,
		isIdle,
	};
};

export default useGetUserInfo;
