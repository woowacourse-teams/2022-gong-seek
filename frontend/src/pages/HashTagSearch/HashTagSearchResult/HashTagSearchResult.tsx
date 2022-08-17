import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import ArticleItem from '@/components/common/ArticleItem/ArticleItem';
import InfiniteScrollObserver from '@/components/common/InfiniteScrollObserver/InfiniteScrollObserver';
import Loading from '@/components/common/Loading/Loading';
import * as S from '@/pages/HashTagSearch/HashTagSearchResult/HashTagSearchResult.styles';
import useGetArticleByHashTag from '@/pages/HashTagSearch/hooks/useGetArticleByHashTag';

export interface HashTagSearchResultProps {
	hashTags: string[];
}

const HashTagSearchResult = ({ hashTags }: HashTagSearchResultProps) => {
	const navigate = useNavigate();
	const { data, isLoading, refetch, fetchNextPage } = useGetArticleByHashTag(hashTags);

	if (isLoading) {
		return <Loading />;
	}

	return (
		<S.Container>
			<h2>검색 결과</h2>
			{data && data.pages[0].articles.length > 1 ? (
				<InfiniteScrollObserver
					hasNext={data.pages[data.pages.length - 1].hasNext}
					fetchNextPage={fetchNextPage}
				>
					<>
						{data.pages.map(({ articles }) =>
							articles.map((article) => (
								<ArticleItem
									key={article.id}
									article={article}
									onClick={() => navigate(`articles/${article.category}/${article.id}`)}
								/>
							)),
						)}
					</>
				</InfiniteScrollObserver>
			) : (
				<S.EmptyMsg>검색 결과가 존재하지 않습니다</S.EmptyMsg>
			)}
		</S.Container>
	);
};

export default HashTagSearchResult;
