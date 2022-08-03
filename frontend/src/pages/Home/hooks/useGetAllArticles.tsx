import { useEffect, useState } from 'react';
import { useInfiniteQuery } from 'react-query';

import { getAllArticle } from '@/api/article';
import { infiniteArticleResponse } from '@/types/articleResponse';

const useGetAllArticles = () => {
	const [currentCategory, setCurrentCategory] = useState('question');
	const [sortIndex, setSortIndex] = useState('최신순');

	const { data, isError, isLoading, isSuccess, error, refetch, fetchNextPage } = useInfiniteQuery<
		infiniteArticleResponse,
		Error
	>(
		['all-articles', currentCategory],
		({
			pageParam = {
				category: currentCategory,
				sort: sortIndex,
				cursorId: '',
				cursorViews: '',
			},
		}) => getAllArticle(pageParam),
		{
			getNextPageParam: (lastPage) => {
				const { hasNext, cursorId, cursorViews } = lastPage;
				if (hasNext) {
					return {
						category: currentCategory,
						sort: sortIndex,
						cursorId,
						cursorViews,
					};
				}
				return;
			},
		},
	);

	useEffect(() => {
		refetch();
	}, [currentCategory, sortIndex]);

	useEffect(() => {
		if (isError) {
			throw new Error(error.message);
		}
	}, [isError]);

	return {
		data,
		isSuccess,
		isLoading,
		currentCategory,
		setCurrentCategory,
		sortIndex,
		setSortIndex,
		fetchNextPage,
	};
};

export default useGetAllArticles;
