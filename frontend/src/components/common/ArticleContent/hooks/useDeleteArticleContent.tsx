import { deleteArticle } from '@/api/article';
import { useEffect } from 'react';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';
import { AxiosError } from 'axios';

const useDeleteArticleContent = () => {
	const { isSuccess, isError, isLoading, error, mutate } = useMutation<unknown, AxiosError, string>(
		deleteArticle,
	);
	const navigate = useNavigate();

	useEffect(() => {
		if (isSuccess) {
			alert('게시글이 삭제 되었습니다');
			navigate('/');
		}
	}, [isSuccess]);

	useEffect(() => {
		if (isError) {
			throw new Error(error.message);
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
