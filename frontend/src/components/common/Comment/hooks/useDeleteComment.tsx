import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';

import { deleteComments } from '@/api/comments';
import CustomError from '@/components/helper/CustomError';
import { queryClient } from '@/index';

const useDeleteComment = () => {
	const { isLoading, isError, error, isSuccess, mutate } = useMutation<
		unknown,
		AxiosError<{ errorCode: string; message: string }>,
		{ commentId: string }
	>(deleteComments);

	const onDeleteButtonClick = (id: number) => {
		if (confirm('정말로 삭제하시겠습니까?')) {
			mutate({ commentId: String(id) });
		}
	};

	useEffect(() => {
		if (isSuccess) {
			queryClient.refetchQueries('comments');
		}
	}, [isSuccess]);

	useEffect(() => {
		if (isError) {
			throw new CustomError(error.response?.data.errorCode, error.response?.data.message);
		}
	}, [isError]);

	return { isLoading, onDeleteButtonClick };
};

export default useDeleteComment;
