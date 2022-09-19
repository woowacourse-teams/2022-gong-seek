import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getTempArticles } from '@/api/tempArticle';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { TempArticleResponse } from '@/types/articleResponse';

const useGetTempArticles = () => {
	const { data, isError, isLoading, error } = useQuery<
		TempArticleResponse,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('temp-articles', getTempArticles, {
		retry: false,
		refetchOnWindowFocus: false,
	});

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

	return { data, isLoading };
};

export default useGetTempArticles;
