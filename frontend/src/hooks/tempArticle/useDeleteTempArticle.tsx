import { AxiosError } from 'axios';
import { useMutation } from 'react-query';

import { deleteArticleItem } from '@/api/tempArticle';
import CustomError from '@/components/@helper/errorBoundary/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useSnackBar from '@/hooks/common/useSnackBar';
import { queryClient } from '@/index';

const useDeleteTempArticle = () => {
	const { mutate } = useMutation<
		any,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{ tempArticleId: number }
	>('temp-article', deleteArticleItem);
	const { showSnackBar } = useSnackBar();

	const deleteTempArticleId = (id: number) => {
		mutate(
			{ tempArticleId: id },
			{
				onSuccess: () => {
					showSnackBar('게시글을 삭제하였습니다');
					queryClient.refetchQueries(['temp-articles']);
				},
				onError: (error) => {
					if (!error.response) {
						return;
					}
					throw new CustomError(
						error.response?.data.errorCode,
						ErrorMessage[error.response?.data.errorCode],
					);
				},
			},
		);
	};

	return { deleteTempArticleId };
};

export default useDeleteTempArticle;
