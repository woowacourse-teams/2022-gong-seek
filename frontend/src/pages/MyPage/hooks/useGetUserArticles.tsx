import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useQuery } from 'react-query';

import { getUserArticles } from '@/api/myPage';
import CustomError from '@/components/helper/CustomError';
import { UserArticlesResponse } from '@/types/articleResponse';

const useGetUserArticles = () => {
	const { data, isSuccess, isLoading, isError, isIdle, error } = useQuery<
		UserArticlesResponse,
		AxiosError<{ errorCode: string; message: string }>
	>('user-articles', getUserArticles);

	useEffect(() => {
		if (isError) {
			throw new CustomError(error.response?.data.errorCode, error.response?.data.message);
		}
	}, [isError]);

	return {
		data,
		isSuccess,
		isLoading,
		isIdle,
	};
};

export default useGetUserArticles;
