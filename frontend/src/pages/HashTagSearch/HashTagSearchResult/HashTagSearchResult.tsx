import { useNavigate } from 'react-router-dom';

import ArticleItem from '@/components/common/ArticleItem/ArticleItem';
import * as S from '@/pages/HashTagSearch/HashTagSearchResult/HashTagSearchResult.styles';
import { CommonArticleType } from '@/types/articleResponse';

export interface HashTagSearchResultProps {
	articles: CommonArticleType[];
}

const HashTagSearchResult = ({ articles }: HashTagSearchResultProps) => {
	const navigate = useNavigate();
	return (
		<S.Container>
			<h2 hidden>해시태그 검색결과가 보여지는 곳입니다</h2>
			{articles && articles.length >= 1 ? (
				<S.SearchResult>
					{articles.map((item) => (
						<ArticleItem
							key={item.id}
							article={item}
							onClick={() => navigate(`articles/${item.category}/${item.id}`)}
						/>
					))}
				</S.SearchResult>
			) : (
				<S.EmptyMsg>검색 결과가 존재하지 않습니다</S.EmptyMsg>
			)}
		</S.Container>
	);
};

export default HashTagSearchResult;
