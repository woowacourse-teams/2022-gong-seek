import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';

import { putComments } from '@/api/comments';
import { CommentInputModalProps } from '@/components/comment/CommentInputModal/CommentInputModal';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useSnackBar from '@/hooks/common/useSnackBar';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const usePutCommentInputModal = (closeModal: CommentInputModalProps['closeModal']) => {
	const { showSnackBar } = useSnackBar();
	const { isLoading, isSuccess, isError, mutate, error } = useMutation<
		unknown,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{ content: string; commentId: string }
	>(putComments, { retry: 1 });

	useThrowCustomError(isError, error);

	useEffect(() => {
		if (isSuccess) {
			showSnackBar('댓글이 수정되었습니다');
			closeModal();
			window.location.reload();
		}
	}, [isSuccess]);

	return { isLoading, mutate, isSuccess };
};

export default usePutCommentInputModal;
