import { AxiosError } from 'axios';
import { useEffect, useState } from 'react';
import { useInfiniteQuery } from 'react-query';

import { getAllArticle } from '@/api/article';
import { ErrorMessage } from '@/constants/ErrorMessage';
import useThrowCustomError from '@/hooks/common/useThrowCustomError';
import { infiniteArticleResponse } from '@/types/articleResponse';

interface useGetAllArticlesProps {
	currentCategory: string;
	sortIndex: string;
}

const useGetAllArticles = ({ currentCategory, sortIndex }: useGetAllArticlesProps) => {
	const { data, isError, error, refetch, fetchNextPage } = useInfiniteQuery<
		infiniteArticleResponse,
		AxiosError<{ errorCode: keyof typeof ErrorMessage; message: string }>
	>(
		['all-articles', currentCategory],
		({
			pageParam = {
				category: currentCategory,
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
						category: currentCategory,
						sort: sortIndex,
						cursorId,
						cursorViews,
						cursorLikes,
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
		refetch,
		fetchNextPage,
	};
};

export default useGetAllArticles;
