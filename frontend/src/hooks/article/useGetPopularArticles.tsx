import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import { getPopularArticles, PopularArticles } from '@/api/article/article';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useGetPopularArticles = () => {
	const { data, error, isError, isLoading } = useQuery<
		PopularArticles,
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
