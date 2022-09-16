import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useRecoilState } from 'recoil';

import { putComments } from '@/api/comments';
import { CommentInputModalProps } from '@/components/common/CommentInputModal/CommentInputModal';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useSnackBar from '@/hooks/common/useSnackBar';
import { errorPortalState } from '@/store/errorPortalState';

const usePutCommentInputModal = (closeModal: CommentInputModalProps['closeModal']) => {
	const { showSnackBar } = useSnackBar();
	const { isLoading, isError, isSuccess, mutate, error } = useMutation<
		unknown,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{ content: string; commentId: string }
	>(putComments, { retry: 1 });

	const [errorPortal, setErrorPortal] = useRecoilState(errorPortalState);

	useEffect(() => {
		if (isError) {
			if (!error.response?.data?.errorCode) {
				setErrorPortal({ isOpen: true });
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response?.data.errorCode],
			);
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
