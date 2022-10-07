import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import { getUserComments } from '@/api/myPage';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { UserCommentResponse } from '@/types/commentResponse';

const useGetUserComments = () => {
	const { data, isSuccess, isError, isLoading, error } = useQuery<
		UserCommentResponse,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('user-comments', getUserComments, { retry: 1, refetchOnWindowFocus: false });

	useThrowCustomError(isError, error);

	return {
		data,
		isSuccess,
		isLoading,
	};
};

export default useGetUserComments;
