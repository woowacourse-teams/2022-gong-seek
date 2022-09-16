import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useInfiniteQuery } from 'react-query';
import { useRecoilState } from 'recoil';

import { getSearchResult } from '@/api/search';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { errorPortalState } from '@/store/errorPortalState';
import { InfiniteSearchResultType } from '@/types/searchResponse';

const useGetSearch = ({ target, searchIndex }: { target: string; searchIndex: string }) => {
	const cursorId = '';
	const [errorPortal, setErrorPortal] = useRecoilState(errorPortalState);

	const { data, isSuccess, isLoading, isError, isIdle, error, refetch, fetchNextPage } =
		useInfiniteQuery<
			InfiniteSearchResultType,
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

	useEffect(() => {
		if (isError) {
			if (!error.response?.data?.errorCode) {
				setErrorPortal({ isOpen: true });
				return;
			}
			throw new CustomError(
				error.response.data.errorCode,
				ErrorMessage[error.response?.data.errorCode],
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
