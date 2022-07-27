import { putComments } from '@/api/comments';
import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { CommentInputModalProps } from '@/components/common/CommentInputModal/CommentInputModal';

const usePutCommentInputModal = (closeModal: CommentInputModalProps['closeModal']) => {
	const { isLoading, isError, isSuccess, mutate, error } = useMutation(putComments);

	useEffect(() => {
		if (isError) {
			throw new Error(error as string);
		}
	}, [isError]);

	useEffect(() => {
		if (isSuccess) {
			alert('댓글이 수정되었습니다');
			closeModal();
		}
	}, [isSuccess]);

	return { isLoading, mutate };
};

export default usePutCommentInputModal;
