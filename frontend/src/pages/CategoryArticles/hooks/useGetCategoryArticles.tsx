import { getAllArticle } from '@/api/article';
import { infiniteArticleResponse } from '@/types/articleResponse';
import { useEffect, useState } from 'react';
import { useInfiniteQuery } from 'react-query';

const useGetCategoryArticles = (category: string) => {
	const [sortIndex, setSortIndex] = useState('최신순');

	const { data, isLoading, isError, isSuccess, error, refetch, fetchNextPage } = useInfiniteQuery<
		infiniteArticleResponse,
		Error
	>(['articles', category], () => getAllArticle({ category, sort: sortIndex }), {
		getNextPageParam: (lastPage) => {
			const { hasNext, cursorId, cursorViews } = lastPage;
			if (hasNext) {
				return {
					category: category,
					sort: sortIndex,
					cursorId,
					cursorViews,
				};
			}
			return undefined;
		},
	});

	useEffect(() => {
		refetch();
	}, [sortIndex]);

	useEffect(() => {
		if (isError) {
			throw new Error(error.message);
		}
	}, [isError]);

	return { data, fetchNextPage, sortIndex, setSortIndex, isLoading , isSuccess};
};

export default useGetCategoryArticles;
