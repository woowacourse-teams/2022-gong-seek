import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';

import { deleteComments } from '@/api/comments';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { queryClient } from '@/index';

const useDeleteComment = () => {
	const { isLoading, isError, error, isSuccess, mutate } = useMutation<
		unknown,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
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
			if (!error.response) {
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response.data.errorCode],
			);
		}
	}, [isError]);

	return { isLoading, onDeleteButtonClick };
};

export default useDeleteComment;
