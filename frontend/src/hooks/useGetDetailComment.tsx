import { getComments } from '@/api/comments';
import { CommentType } from '@/types/commentResponse';
import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

const useGetDetailComment = (id: string) => {
	const { data, isError, isSuccess, isLoading, isIdle, error } = useQuery<
		{ comments: CommentType[] },
		AxiosError
	>('comments', () => getComments(id));

	useEffect(() => {
		if (isError) {
			throw new Error(error.message);
		}
	}, [isError]);

	return { isLoading, isSuccess, data, isIdle };
};

export default useGetDetailComment;
