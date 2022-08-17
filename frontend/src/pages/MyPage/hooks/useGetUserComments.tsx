import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getUserComments } from '@/api/myPage';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { UserCommentResponse } from '@/types/commentResponse';

const useGetUserComments = () => {
	const { data, isSuccess, isLoading, isIdle, isError, error } = useQuery<
		UserCommentResponse,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('user-comments', getUserComments, { retry: false });

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

	return {
		data,
		isSuccess,
		isLoading,
		isIdle,
	};
};

export default useGetUserComments;
