import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';

import { postComments } from '@/api/comments';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useSnackBar from '@/hooks/common/useSnackBar';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const usePostCommentInputModal = (hideModal: () => void) => {
	const { showSnackBar } = useSnackBar();
	const { isLoading, error, isError, isSuccess, mutate } = useMutation<
		unknown,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{ content: string; id: string; isAnonymous: boolean }
	>(postComments, { retry: 1 });

	useThrowCustomError(isError, error);

	useEffect(() => {
		if (isSuccess) {
			showSnackBar('댓글이 등록되었습니다');
			hideModal();
		}
	}, [isSuccess]);

	return { isLoading, isSuccess, mutate };
};

export default usePostCommentInputModal;
