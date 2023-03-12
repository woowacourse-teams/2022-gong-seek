import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import { getPopularArticles } from '@/api/article/article';
import { TotalArticleInquiredResponseType } from '@/api/article/articleType';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useGetPopularArticles = () => {
	const { data, error, isError, isLoading } = useQuery<
		TotalArticleInquiredResponseType,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('popular-articles', getPopularArticles, {
		retry: 1,
		refetchOnWindowFocus: false,
	});

	useThrowCustomError(isError, error);

	return {
		data,
		isLoading,
	};
};

export default useGetPopularArticles;
