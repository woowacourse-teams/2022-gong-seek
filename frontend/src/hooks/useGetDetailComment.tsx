import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getComments } from '@/api/comments';
import CustomError from '@/components/helper/CustomError';
import { CommentType } from '@/types/commentResponse';

const useGetDetailComment = (id: string) => {
	const { data, isError, isSuccess, isLoading, isIdle, error } = useQuery<
		{ comments: CommentType[] },
		AxiosError<{ errorCode: string; message: string }>
	>('comments', () => getComments(id));

	useEffect(() => {
		if (isError) {
			throw new CustomError(error.response?.data.errorCode, error.response?.data.message);
		}
	}, [isError]);

	return { isLoading, isSuccess, data, isIdle };
};

export default useGetDetailComment;
