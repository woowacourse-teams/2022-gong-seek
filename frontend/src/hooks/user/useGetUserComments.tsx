import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import { MyPageCommentResponse } from '@/api/comment/commentType';
import { getUserComments } from '@/api/user/myPage';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useGetUserComments = () => {
	const { data, isSuccess, isError, isLoading, error } = useQuery<
		MyPageCommentResponse,
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
