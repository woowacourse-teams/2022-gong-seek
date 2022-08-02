import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import ArticleItem from '@/components/common/ArticleItem/ArticleItem';
import InfiniteScrollObserver from '@/components/common/InfiniteScrollObserver/InfiniteScrollObserver';
import Loading from '@/components/common/Loading/Loading';
import * as S from '@/pages/Search/SearchResult/SearchResult.styles';
import useGetSearch from '@/pages/Search/hooks/useGetSearch';

const SearchResult = ({ target }: { target: string }) => {
	const { data, isLoading, isIdle, refetch, fetchNextPage } = useGetSearch(target);
	const navigate = useNavigate();

	useEffect(() => {
		refetch();
	}, [target]);

	if (isLoading || isIdle) {
		return <Loading />;
	}

	return (
		<S.Container>
			<S.Title>검색 결과</S.Title>
			{data && data.pages[0].articles.length >= 1 ? (
				<InfiniteScrollObserver
					hasNext={data.pages[data.pages.length - 1].hasNext}
					fetchNextPage={fetchNextPage}
				>
					<S.SearchResultBox>
						{data.pages.map(({ articles }) =>
							articles.map((article) => (
								<ArticleItem
									key={article.id}
									article={article}
									onClick={() => {
										navigate(`/articles/${article.category}/${article.id}`);
									}}
								/>
							)),
						)}
					</S.SearchResultBox>
				</InfiniteScrollObserver>
			) : (
				<div>검색 결과가 존재하지 않습니다</div>
			)}
		</S.Container>
	);
};

export default SearchResult;
