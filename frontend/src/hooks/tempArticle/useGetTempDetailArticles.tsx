import { AxiosError, AxiosResponse } from 'axios';
import { useEffect } from 'react';
import { useMutation } from 'react-query';

import { getTempDetailArticle } from '@/api/tempArticle';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { TempArticleDetailResponse } from '@/types/articleResponse';

const useGetTempDetailArticles = ({ tempArticleId }: { tempArticleId: number | '' }) => {
	const { data, isLoading, isSuccess, isError, error, mutate } = useMutation<
		AxiosResponse<TempArticleDetailResponse>,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>,
		{
			tempArticleId: number;
		}
	>(['temp-detail-article', tempArticleId], () => getTempDetailArticle({ id: tempArticleId }));

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

	return { data, isLoading, isSuccess, mutate };
};

export default useGetTempDetailArticles;
