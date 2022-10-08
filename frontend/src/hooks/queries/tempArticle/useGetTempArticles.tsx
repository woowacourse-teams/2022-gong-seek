import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import { getTempArticles } from '@/api/tempArticle';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { TempArticleResponse } from '@/types/articleResponse';

const useGetTempArticles = () => {
	const { data, isError, isLoading, error } = useQuery<
		TempArticleResponse,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('temp-articles', getTempArticles, {
		retry: false,
		refetchOnWindowFocus: false,
	});

	useThrowCustomError(isError, error);

	return { data, isLoading };
};

export default useGetTempArticles;
