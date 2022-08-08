import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useInfiniteQuery } from 'react-query';

import { getSearchResult } from '@/api/search';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { InfiniteSearchResultType } from '@/types/searchResponse';

const useGetSearch = (target: string) => {
	const cursorId = '';
	const { data, isSuccess, isLoading, isError, isIdle, error, refetch, fetchNextPage } =
		useInfiniteQuery<
			InfiniteSearchResultType,
			AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
		>('search-result', () => getSearchResult({ target, cursorId }), {
			getNextPageParam: (lastPage) => {
				const { hasNext, articles } = lastPage;

				if (hasNext && articles.length >= 1) {
					const lastCursorId = articles[articles.length - 1].id;
					return {
						articles,
						hasNext,
						lastCursorId,
					};
				}
				return undefined;
			},
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

	return {
		data,
		isSuccess,
		isLoading,
		isIdle,
		refetch,
		fetchNextPage,
	};
};

export default useGetSearch;
