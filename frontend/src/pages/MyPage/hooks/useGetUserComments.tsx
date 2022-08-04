import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getUserComments } from '@/api/myPage';
import CustomError from '@/components/helper/CustomError';
import { UserCommentResponse } from '@/types/commentResponse';

const useGetUserComments = () => {
	const { data, isSuccess, isLoading, isIdle, isError, error } = useQuery<
		UserCommentResponse,
		AxiosError<{ errorCode: string; message: string }>
	>('user-comments', getUserComments);

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

export default useGetUserComments;
