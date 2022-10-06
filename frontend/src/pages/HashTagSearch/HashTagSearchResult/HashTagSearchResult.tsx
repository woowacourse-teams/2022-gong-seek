import { useNavigate } from 'react-router-dom';

import ArticleItem from '@/components/common/ArticleItem/ArticleItem';
import Loading from '@/components/common/Loading/Loading';
import ResponsiveInfiniteCardList from '@/components/common/ResponsiveInfiniteCardList/ResponsiveInfiniteCardList';
import useGetArticleByHashTag from '@/hooks/hashTag/useGetArticleByHashTag';
import * as S from '@/pages/HashTagSearch/HashTagSearchResult/HashTagSearchResult.styles';
import { EmptyMessage } from '@/pages/Search/index.styles';

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
									onClick={() => navigate(`/articles/${article.category}/${article.id}`)}
								/>
							)),
						)}
					</>
				</ResponsiveInfiniteCardList>
			) : (
				<EmptyMessage>검색결과가 존재하지 않습니다</EmptyMessage>
			)}
		</S.Container>
	);
};

export default HashTagSearchResult;
