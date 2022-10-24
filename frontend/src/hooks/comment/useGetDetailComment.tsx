import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import { TotalCommentResponseType } from '@/api/comment/commentType';
import { getComments } from '@/api/comment/comments';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useGetDetailComment = (id: string) => {
	const { data, isSuccess, isError, isLoading, isIdle, error } = useQuery<
		TotalCommentResponseType,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('comments', () => getComments(id), { retry: false, refetchOnWindowFocus: false });

	useThrowCustomError(isError, error);

	return { isLoading, isSuccess, data, isIdle };
};

export default useGetDetailComment;
