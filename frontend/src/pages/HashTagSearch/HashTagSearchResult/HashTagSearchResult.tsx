import { useNavigate } from 'react-router-dom';

import ArticleItem from '@/components/common/ArticleItem/ArticleItem';
import InfiniteScrollObserver from '@/components/common/InfiniteScrollObserver/InfiniteScrollObserver';
import Loading from '@/components/common/Loading/Loading';
import useGetArticleByHashTag from '@/hooks/hashTag/useGetArticleByHashTag';
import * as S from '@/pages/HashTagSearch/HashTagSearchResult/HashTagSearchResult.styles';

export interface HashTagSearchResultProps {
	hashTags: string[];
}

const HashTagSearchResult = ({ hashTags }: HashTagSearchResultProps) => {
	const navigate = useNavigate();

	const { data, isLoading, fetchNextPage } = useGetArticleByHashTag(hashTags);

	if (isLoading) {
		return <Loading />;
	}

	return (
		<S.Container>
			<S.Title>검색 결과</S.Title>
			{/*TODO: 하나의 List로 묶어야한다. */}
			{data && data.pages[0].articles.length >= 1 ? (
				<InfiniteScrollObserver
					hasNext={data.pages[data.pages.length - 1].hasNext}
					fetchNextPage={fetchNextPage}
				>
					<S.SearchResult>
						{data.pages.map(({ articles }) =>
							articles.map((article) => (
								<ArticleItem
									key={article.id}
									article={article}
									onClick={() => navigate(`/articles/${article.category}/${article.id}`)}
								/>
							)),
						)}
					</S.SearchResult>
				</InfiniteScrollObserver>
			) : (
				<S.EmptyMsg>검색 결과가 존재하지 않습니다</S.EmptyMsg>
			)}
		</S.Container>
	);
};

export default HashTagSearchResult;
