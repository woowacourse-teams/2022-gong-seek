import TemporaryArticleList from '@/pages/TemporaryArticles/TemporaryArticleList/TemporaryArticleList';
import * as S from '@/pages/TemporaryArticles/index.styles';

const TemporaryArticles = () => (
	<S.Container>
		<S.Header>
			<S.Title>임시저장 글</S.Title>
		</S.Header>
		<S.ArticleListBox>
			<TemporaryArticleList />
		</S.ArticleListBox>
	</S.Container>
);

export default TemporaryArticles;
