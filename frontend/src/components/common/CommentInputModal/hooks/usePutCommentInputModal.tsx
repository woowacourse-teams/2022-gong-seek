import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';

import { putComments } from '@/api/comments';
import { CommentInputModalProps } from '@/components/common/CommentInputModal/CommentInputModal';
import useSnackBar from '@/hooks/useSnackBar';

const usePutCommentInputModal = (closeModal: CommentInputModalProps['closeModal']) => {
	const { showSnackBar } = useSnackBar();
	const { isLoading, isError, isSuccess, mutate, error } = useMutation<
		unknown,
		AxiosError,
		{ content: string; commentId: string }
	>(putComments);

	useEffect(() => {
		if (isError) {
			throw new Error(error.message);
		}
	}, [isError]);

	useEffect(() => {
		if (isSuccess) {
			showSnackBar('댓글이 수정되었습니다');
			closeModal();
		}
	}, [isSuccess]);

	return { isLoading, mutate, isSuccess };
};

export default usePutCommentInputModal;
