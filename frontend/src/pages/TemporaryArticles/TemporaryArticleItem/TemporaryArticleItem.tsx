import * as S from '@/pages/TemporaryArticles/TemporaryArticleItem/TemporaryArticleItem.styles';
import { dateTimeConverter } from '@/utils/converter';

export interface TemporaryArticleItemProps {
	article: { title: string; createAt: string; category: string };
	onClick: () => void;
}

const TemporaryArticleItem = ({ article, onClick }: TemporaryArticleItemProps) => (
	<S.Container onClick={onClick}>
		<S.Title>{article.title}</S.Title>
		<S.SubInfo>
			<S.Category isQuestion={article.category === 'question'}>
				{article.category === 'question' ? '질문' : '토론'}
			</S.Category>
			<S.CreatedAt>{article.createAt && dateTimeConverter(article.createAt)}</S.CreatedAt>
		</S.SubInfo>
	</S.Container>
);

export default TemporaryArticleItem;
