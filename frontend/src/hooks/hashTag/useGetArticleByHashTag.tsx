import { AxiosError } from 'axios';
import { useEffect } from 'react';
import { useInfiniteQuery } from 'react-query';
import { useRecoilState } from 'recoil';

import { getArticleByHashTag } from '@/api/search';
import CustomError from '@/components/helper/CustomError';
import { ErrorMessage } from '@/constants/ErrorMessage';
import { errorPortalState } from '@/store/errorPortalState';
import { InfiniteHashTagSearchResultType } from '@/types/searchResponse';

const useGetArticleByHashTag = (hashTag: string[]) => {
	const tags = hashTag.join(',');
	const cursorId = '';
	const [errorPortal, setErrorPortal] = useRecoilState(errorPortalState);

	const { data, isError, isLoading, isSuccess, error, refetch, fetchNextPage } = useInfiniteQuery<
		InfiniteHashTagSearchResultType,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>(
		['hashtag-search-result', tags],
		({ pageParam = { hashTags: tags, cursorId } }) => getArticleByHashTag(pageParam),
		{
			getNextPageParam: (lastPage) => {
				const { hasNext, articles, cursorId, hashTags } = lastPage;
				if (hasNext) {
					return {
						articles,
						hasNext,
						cursorId,
						hashTags,
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

	useEffect(() => {
		refetch();
	}, [hashTag]);

	return { data, isLoading, isSuccess, refetch, fetchNextPage };
};

export default useGetArticleByHashTag;
