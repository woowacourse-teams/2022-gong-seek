import { AxiosError } from 'axios';
import { useQuery } from 'react-query';

import { getUserArticles } from '@/api/myPage';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { UserArticlesResponse } from '@/types/articleResponse';

const useGetUserArticles = () => {
	const { data, isSuccess, isError, isLoading, error } = useQuery<
		UserArticlesResponse,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>('user-articles', getUserArticles, { retry: 1, refetchOnWindowFocus: false });

	useThrowCustomError(isError, error);

	return {
		data,
		isSuccess,
		isLoading,
	};
};

export default useGetUserArticles;
