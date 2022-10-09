import { AxiosError } from 'axios';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { deleteArticle } from '@/api/article';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { URL } from '@/constants/url';
import useSnackBar from '@/hooks/common/useSnackBar';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useDeleteArticleContent = () => {
	const { showSnackBar } = useSnackBar();
	const navigate = useNavigate();

	const { isError, error, mutate } = useMutation<
		unknown,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		string
	>(deleteArticle, {
		retry: 1,
		onSuccess: () => {
			showSnackBar('게시글이 삭제 되었습니다');
			navigate(URL.HOME);
		},
	});

	useThrowCustomError(isError, error);

	const handleDeleteArticle = (articleId: string) => {
		if (window.confirm('게시글을 삭제하시겠습니까?')) {
			mutate(articleId);
		}
	};

	return { handleDeleteArticle };
};

export default useDeleteArticleContent;
