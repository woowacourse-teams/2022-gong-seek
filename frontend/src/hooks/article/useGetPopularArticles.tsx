import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getPopularArticles, PopularArticles } from '@/api/article';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useGetPopularArticles = (initCarousel: (maxArticleLength: number) => void) => {
	const { data, error, isError, isSuccess, isLoading } = useQuery<
		PopularArticles,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('popular-articles', getPopularArticles, {
		retry: 1,
		refetchOnWindowFocus: false,
	});

	useEffect(() => {
		if (isSuccess) {
			initCarousel(data.articles.length);
		}
	}, [isSuccess]);

	useThrowCustomError(isError, error);

	return {
		data,
		isLoading,
	};
};

export default useGetPopularArticles;
