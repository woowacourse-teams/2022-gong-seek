import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import { getComments } from '@/api/comments';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { CommentType } from '@/types/commentResponse';

const useGetDetailComment = (id: string) => {
	const { data, isSuccess, isLoading, isIdle, error } = useQuery<
		{ comments: CommentType[] },
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('comments', () => getComments(id), { retry: false, refetchOnWindowFocus: false });

	useThrowCustomError(error);

	return { isLoading, isSuccess, data, isIdle };
};

export default useGetDetailComment;
