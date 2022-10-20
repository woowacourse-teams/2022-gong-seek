import { AxiosError, AxiosResponse } from 'axios';
import { useMutation } from 'react-query';

import { getTempDetailArticle } from '@/api/tempArticle/tempArticle';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { TempArticleDetailResponse } from '@/types/articleResponse';

const useGetTempDetailArticles = ({ tempArticleId }: { tempArticleId: number | '' }) => {
	const { data, isLoading, isSuccess, isError, error, mutate } = useMutation<
		AxiosResponse<TempArticleDetailResponse>,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{
			tempArticleId: number;
		}
	>(['temp-detail-article', tempArticleId], () => getTempDetailArticle({ id: tempArticleId }));

	useThrowCustomError(isError, error);

	return { data, isLoading, isSuccess, mutate };
};

export default useGetTempDetailArticles;
