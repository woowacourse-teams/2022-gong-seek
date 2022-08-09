import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { deleteArticle } from '@/api/article';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useSnackBar from '@/hooks/useSnackBar';

const useDeleteArticleContent = () => {
	const { showSnackBar } = useSnackBar();
	const { isSuccess, isError, isLoading, error, mutate } = useMutation<
		unknown,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		string
	>(deleteArticle);
	const navigate = useNavigate();

	useEffect(() => {
		if (isSuccess) {
			showSnackBar('게시글이 삭제 되었습니다');
			navigate('/');
		}
	}, [isSuccess]);

	useEffect(() => {
		if (isError) {
			if (!error.response) {
				return;
			}

			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response.data.errorCode],
			);
		}
	}, [isError]);

	const handleDeleteArticle = (articleId: string) => {
		if (window.confirm('게시글을 삭제하시겠습니까?')) {
			mutate(articleId);
		}
	};

	return { isLoading, handleDeleteArticle };
};

export default useDeleteArticleContent;
