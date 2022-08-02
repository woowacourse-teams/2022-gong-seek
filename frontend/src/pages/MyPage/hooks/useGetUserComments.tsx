import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getUserComments } from '@/api/myPage';
import { UserCommentResponse } from '@/types/commentResponse';

const useGetUserComments = () => {
	const { data, isSuccess, isLoading, isIdle, isError, error } = useQuery<
		UserCommentResponse,
		Error
	>('user-comments', getUserComments);

	useEffect(() => {
		if (isError) {
			throw new Error(error.message);
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
