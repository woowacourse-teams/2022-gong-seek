import { queryClient } from '@/index';
import { deleteComments } from '@/api/comments';
import { useEffect } from 'react';
import { useMutation } from 'react-query';

const useDeleteComment = () => {
	const { isLoading, isError, error, isSuccess, mutate } = useMutation(deleteComments);

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
			throw new Error(error as string);
		}
	}, [isError]);

	return { isLoading, onDeleteButtonClick };
};

export default useDeleteComment;
