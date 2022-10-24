import React, { Suspense, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { ArticleTotalType } from '@/api/article/articleType';
import EmptyMessage from '@/components/@common/EmptyMessage/EmptyMessage';
import Loading from '@/components/@common/Loading/Loading';
import ArticleItem from '@/components/article/ArticleItem/ArticleItem';
import * as S from '@/components/search/SearchResult/SearchResult.styles';
import useGetSearch from '@/hooks/search/useGetSearch';

const ResponsiveInfiniteCardList = React.lazy(
	() => import('@/components/@common/ResponsiveInfiniteCardList/ResponsiveInfiniteCardList'),
);

const SearchResult = ({ target, searchIndex }: { target: string; searchIndex: string }) => {
	const { data, isLoading, isIdle, refetch, fetchNextPage } = useGetSearch({ target, searchIndex });
	const navigate = useNavigate();

	useEffect(() => {
		refetch();
	}, [target, searchIndex]);

	const handleClickArticleItem = (
		article: Omit<ArticleTotalType, 'updatedAt' | 'hasVote' | 'isAuthor'>,
	) => {
		navigate(`/articles/${article.category}/${article.id}`);
	};

	if (isLoading || isIdle) {
		return <Loading />;
	}

	return (
		<S.Container>
			<S.Title>검색 결과</S.Title>
			<Suspense fallback={<Loading />}>
				{data && data.pages[0].articles.length >= 1 ? (
					<ResponsiveInfiniteCardList
						hasNext={data.pages[data.pages.length - 1].hasNext}
						fetchNextPage={fetchNextPage}
					>
						<>
							{data.pages.map(({ articles }) =>
								articles.map((article) => (
									<ArticleItem
										key={article.id}
										article={article}
										onClick={() => handleClickArticleItem(article)}
									/>
								)),
							)}
						</>
					</ResponsiveInfiniteCardList>
				) : (
					<EmptyMessage>검색 결과가 존재하지 않습니다</EmptyMessage>
				)}
			</Suspense>
		</S.Container>
	);
};

export default SearchResult;
