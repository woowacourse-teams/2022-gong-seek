import { AllArticleResponse } from '@/types/articleResponse';
import { useInfiniteQuery } from 'react-query';
import { getAllArticle } from '@/api/article';
import { useEffect, useState } from 'react';

interface infiniteArticleResponse extends AllArticleResponse {
	cursorId: number;
	cursorViews: number;
}

const useGetAllArticles = () => {
	const [currentCategory, setCurrentCategory] = useState('question');
	const [sortIndex, setSortIndex] = useState('최신순');
	const { data, isError, isLoading, isSuccess, error, refetch } = useInfiniteQuery<
		infiniteArticleResponse,
		Error
	>(
		['all-articles', currentCategory],
		() => getAllArticle({ category: currentCategory, sort: sortIndex }),
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
				return undefined;
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
	};
};

export default useGetAllArticles;
