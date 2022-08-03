import { postComments } from '@/api/comments';
import { useMutation } from 'react-query';
import { useEffect } from 'react';
import { CommentInputModalProps } from '@/components/common/CommentInputModal/CommentInputModal';
import { AxiosError } from 'axios';
import useSnackBar from '@/hooks/useSnackBar';

const usePostCommentInputModal = (closeModal: CommentInputModalProps['closeModal']) => {
	const {showSnackBar} = useSnackBar();
	const { isLoading, isError, error, isSuccess, mutate } = useMutation<
		unknown,
		AxiosError,
		{ content: string; id: string }
	>(postComments);

	useEffect(() => {
		if (isError) {
			throw new Error(error.message);
		}
	}, [isError]);

	useEffect(() => {
		if (isSuccess) {
			showSnackBar('댓글이 등록되었습니다');
			closeModal();
		}
	}, [isSuccess]);

	return { isLoading,isSuccess, mutate };
};

export default usePostCommentInputModal;
