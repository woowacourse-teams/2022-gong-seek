import { useNavigate } from 'react-router-dom';

import { ArticleTotalType } from '@/api/article/articleType';
import Loading from '@/components/@common/Loading/Loading';
import ResponsiveInfiniteCardList from '@/components/@common/ResponsiveInfiniteCardList/ResponsiveInfiniteCardList';
import ArticleItem from '@/components/article/ArticleItem/ArticleItem';
import * as S from '@/components/hashTag/HashTagSearchResult/HashTagSearchResult.styles';
import useGetArticleByHashTag from '@/hooks/hashTag/useGetArticleByHashTag';
import { EmptyMessage } from '@/pages/Search/index.styles';

export interface HashTagSearchResultProps {
	hashTags: string[];
}

const HashTagSearchResult = ({ hashTags }: HashTagSearchResultProps) => {
	const navigate = useNavigate();

	const { data, isLoading, fetchNextPage } = useGetArticleByHashTag(hashTags);

	const handleClickArticleItem = (
		article: Omit<ArticleTotalType, 'updatedAt' | 'hasVote' | 'isAuthor'>,
	) => {
		navigate(`/articles/${article.category}/${article.id}`);
	};

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
									onClick={() => handleClickArticleItem(article)}
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
