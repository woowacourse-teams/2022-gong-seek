import { postComments } from '@/api/comments';
import { useMutation } from 'react-query';
import { useEffect } from 'react';
import { CommentInputModalProps } from '@/components/common/CommentInputModal/CommentInputModal';

const usePostCommentInputModal = (closeModal: CommentInputModalProps['closeModal']) => {
	const { isLoading, isError, error, isSuccess, mutate } = useMutation(postComments);

	useEffect(() => {
		if (isError) {
			throw new Error(error as string);
		}
	}, [isError]);

	useEffect(() => {
		if (isSuccess) {
			alert('댓글이 등록되었습니다.');
			closeModal();
		}
	}, [isSuccess]);

	return { isLoading, mutate };
};

export default usePostCommentInputModal;
