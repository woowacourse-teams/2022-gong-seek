import * as S from '@/pages/TemporaryArticles/TemporaryArticleItem/TemporaryArticleItem.styles';
import { dateTimeConverter } from '@/utils/converter';

export interface TemporaryArticleItemProps {
	article: { title: string; createAt: string };
	onClick: () => void;
}

const TemporaryArticleItem = ({ article, onClick }: TemporaryArticleItemProps) => (
	<S.Container onClick={onClick}>
		<S.Title>{article.title}</S.Title>
		<S.CreatedAt>{article.createAt && dateTimeConverter(article.createAt)}</S.CreatedAt>
	</S.Container>
);

export default TemporaryArticleItem;
