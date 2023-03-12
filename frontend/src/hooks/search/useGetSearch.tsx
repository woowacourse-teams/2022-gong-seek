import { AxiosError } from 'axios';
import { useInfiniteQuery } from 'react-query';

import { getSearchResult } from '@/api/search/search';
import { InfiniteArticleSearchResponseType } from '@/api/search/searchType';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';

const useGetSearch = ({ target, searchIndex }: { target: string; searchIndex: string }) => {
	const cursorId = '';
	const { data, isSuccess, isLoading, isError, isIdle, error, refetch, fetchNextPage } =
		useInfiniteQuery<
			InfiniteArticleSearchResponseType,
			AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
		>(
			['search-result', `${searchIndex}-${target}`],
			({
				pageParam = {
					target,
					searchIndex,
					cursorId,
				},
			}) => getSearchResult(pageParam),
			{
				getNextPageParam: (lastPage) => {
					const { hasNext, articles, cursorId, target, searchIndex } = lastPage;

					if (hasNext) {
						return {
							articles,
							hasNext,
							cursorId,
							target,
							searchIndex,
						};
					}
					return;
				},
				retry: 1,
				refetchOnWindowFocus: false,
			},
		);

	useThrowCustomError(isError, error);

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
