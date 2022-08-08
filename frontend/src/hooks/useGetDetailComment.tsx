import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getComments } from '@/api/comments';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { CommentType } from '@/types/commentResponse';

const useGetDetailComment = (id: string) => {
	const { data, isError, isSuccess, isLoading, isIdle, error } = useQuery<
		{ comments: CommentType[] },
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('comments', () => getComments(id));

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

	return { isLoading, isSuccess, data, isIdle };
};

export default useGetDetailComment;
