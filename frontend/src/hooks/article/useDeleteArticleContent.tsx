import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { deleteArticle } from '@/api/article/article';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { URL } from '@/constants/url';
import useSnackBar from '@/hooks/common/useSnackBar';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useDeleteArticleContent = () => {
	const { showSnackBar } = useSnackBar();
	const { isSuccess, isError, error, mutate } = useMutation<
		unknown,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		string
	>(deleteArticle, { retry: 1 });
	const navigate = useNavigate();

	useThrowCustomError(isError, error);

	useEffect(() => {
		if (isSuccess) {
			showSnackBar('게시글이 삭제 되었습니다');
			navigate(URL.HOME);
		}
	}, [isSuccess]);

	const handleDeleteArticle = (articleId: string) => {
		if (window.confirm('게시글을 삭제하시겠습니까?')) {
			mutate(articleId);
		}
	};

	return { handleDeleteArticle };
};

export default useDeleteArticleContent;
