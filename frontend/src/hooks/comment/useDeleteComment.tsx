import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';

import { deleteComments } from '@/api/comment/comments';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { queryClient } from '@/index';

const useDeleteComment = () => {
	const { isLoading, error, isError, isSuccess, mutate } = useMutation<
		unknown,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{ commentId: string }
	>(deleteComments, { retry: 1 });

	useThrowCustomError(isError, error);

	const handleClickCommentDeleteButton = (id: number) => {
		if (confirm('정말로 삭제하시겠습니까?')) {
			mutate({ commentId: String(id) });
		}
	};

	useEffect(() => {
		if (isSuccess) {
			queryClient.refetchQueries('comments');
		}
	}, [isSuccess]);

	return { isLoading, handleClickCommentDeleteButton };
};

export default useDeleteComment;
