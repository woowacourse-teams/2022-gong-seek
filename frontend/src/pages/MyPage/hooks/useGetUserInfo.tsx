import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getUserInfo } from '@/api/myPage';
import CustomError from '@/components/helper/CustomError';
import { Author } from '@/types/author';

const useGetUserInfo = () => {
	const { data, isSuccess, isError, isLoading, isIdle, error } = useQuery<
		Author,
		AxiosError<{ errorCode: string; message: string }>
	>('user-info', getUserInfo);

	useEffect(() => {
		if (isError) {
			throw new CustomError(error.response?.data.errorCode, error.response?.data.message);
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
