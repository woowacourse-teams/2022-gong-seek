import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useInfiniteQuery } from 'react-query';

import { getAllArticle } from '@/api/article/article';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { infiniteArticleResponse } from '@/types/articleResponse';

const useGetCategoryArticles = (category: string) => {
	const [sortIndex, setSortIndex] = useState('최신순');

	const { data, isLoading, isError, isSuccess, error, refetch, fetchNextPage } = useInfiniteQuery<
		infiniteArticleResponse,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>(
		['articles', category],
		({
			pageParam = {
				category,
				sort: sortIndex,
				cursorId: '',
				cursorViews: '',
				cursorLikes: '',
			},
		}) => getAllArticle(pageParam),
		{
			getNextPageParam: (lastPage) => {
				const { hasNext, cursorId, cursorViews, cursorLikes } = lastPage;
				if (hasNext) {
					return {
						category: category,
						sort: sortIndex,
						cursorId,
						cursorViews,
						cursorLikes,
					};
				}
				return;
			},
			retry: false,
			refetchOnWindowFocus: false,
		},
	);

	useThrowCustomError(isError, error);

	useEffect(() => {
		refetch();
	}, [sortIndex]);

	return { data, fetchNextPage, sortIndex, setSortIndex, isLoading, isSuccess };
};

export default useGetCategoryArticles;
