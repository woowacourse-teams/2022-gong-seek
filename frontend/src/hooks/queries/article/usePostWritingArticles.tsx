import { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { postWritingArticle } from '@/api/article';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { CATEGORY } from '@/constants/categoryType';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const usePostWritingArticles = ({ categoryOption }: { categoryOption: string }) => {
	const navigate = useNavigate();

	const { mutate, isError, isLoading, error } = useMutation<
		AxiosResponse<{ id: string }>,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{
			title: string;
			category: string;
			content: string;
			tag: string[];
			isAnonymous: boolean;
			tempArticleId: number | '';
		}
	>(postWritingArticle, {
		retry: 1,
		onSuccess: (data) => {
			if (categoryOption === CATEGORY.discussion) {
				if (confirm('글 등록이 완료되었습니다. 투표를 등록하시겠습니까?')) {
					navigate(`/votes/${data.data.id}`);
					return;
				}
				navigate(`/articles/${categoryOption}/${data.data.id}`);
			}
			if (categoryOption === CATEGORY.question) {
				navigate(`/articles/${categoryOption}/${data.data.id}`);
			}
		},
	});

	useThrowCustomError(isError, error);

	return {
		isLoading,
		mutate,
	};
};

export default usePostWritingArticles;
